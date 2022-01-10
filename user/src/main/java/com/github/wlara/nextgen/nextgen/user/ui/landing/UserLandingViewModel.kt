package com.github.wlara.nextgen.nextgen.user.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wlara.nextgen.nextgen.core.ext.friendlyMessage
import com.github.wlara.nextgen.nextgen.user.model.User
import com.github.wlara.nextgen.nextgen.user.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

data class UserLandingUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class UserLandingViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserLandingUiState())

    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val users = repository.getAllUsers()
                _uiState.update { it.copy(users = users, isLoading = false) }
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

