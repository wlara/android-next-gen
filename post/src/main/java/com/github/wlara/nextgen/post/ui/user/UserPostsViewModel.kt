package com.github.wlara.nextgen.post.ui.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wlara.nextgen.core.ext.friendlyMessage
import com.github.wlara.nextgen.core.navigation.ARG_USER_ID
import com.github.wlara.nextgen.post.model.Post
import com.github.wlara.nextgen.post.repo.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

data class UserPostsUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class UserPostsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserPostsUiState())

    val uiState = _uiState.asStateFlow()

    private val userId = savedStateHandle.get<Int>(ARG_USER_ID)!!

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val posts = repository.getUserPosts(userId)
                _uiState.update { it.copy(posts = posts, isLoading = false) }
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

