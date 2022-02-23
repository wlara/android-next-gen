package com.github.wlara.nextgen.comment.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wlara.nextgen.core.ext.friendlyMessage
import com.github.wlara.nextgen.comment.model.Comment
import com.github.wlara.nextgen.comment.repo.CommentRepository
import com.github.wlara.nextgen.core.navigation.ArgCommentId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

data class CommentDetailsUiState(
    val comment: Comment? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class CommentDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CommentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommentDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val commentId = savedStateHandle.get<Int>(ArgCommentId)!!

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val comment = repository.getComment(commentId)
                _uiState.update { it.copy(comment = comment, isLoading = false) }
            } catch (e: CancellationException) {
                _uiState.update { it.copy(isLoading = false) }
                throw e
            } catch (e: Throwable) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.friendlyMessage) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

