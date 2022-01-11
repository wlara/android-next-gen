package com.github.wlara.nextgen.user.network

import com.github.wlara.nextgen.user.model.User

interface UserService {
    suspend fun getUser(userId: Int): User
    suspend fun getAllUsers(): List<User>
}