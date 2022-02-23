package com.github.wlara.nextgen.comment.ui.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wlara.nextgen.core.ext.friendlyMessage
import com.github.wlara.nextgen.comment.model.Comment
import com.github.wlara.nextgen.comment.repo.CommentRepository
import com.github.wlara.nextgen.core.navigation.ArgPostId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

data class PostCommentsUiState(
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class PostCommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CommentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostCommentsUiState())

    val uiState = _uiState.asStateFlow()

    private val postId = savedStateHandle.get<Int>(ArgPostId)!!

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val comments = repository.getPostComments(postId)
                _uiState.update { it.copy(comments = comments, isLoading = false) }
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

