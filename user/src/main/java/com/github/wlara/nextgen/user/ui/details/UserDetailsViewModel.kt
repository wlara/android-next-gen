package com.github.wlara.nextgen.user.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wlara.nextgen.core.ext.friendlyMessage
import com.github.wlara.nextgen.core.navigation.ArgUserId
import com.github.wlara.nextgen.user.model.User
import com.github.wlara.nextgen.user.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

data class UserDetailsUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val userId = savedStateHandle.get<Int>(ArgUserId)!!

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val user = repository.getUser(userId)
                _uiState.update { it.copy(user = user, isLoading = false) }
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

