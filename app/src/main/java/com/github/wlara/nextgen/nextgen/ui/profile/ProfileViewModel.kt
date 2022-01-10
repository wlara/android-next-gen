package com.github.wlara.nextgen.nextgen.ui.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wlara.nextgen.nextgen.user.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("LogNotTimber")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUsers() {
        viewModelScope.launch {
            try {
               userRepository.getAllUsers()
            } catch (e: Throwable) {
                Log.e("ProfileViewModel", "getAllUsers failed", e)
            }
        }
    }
}