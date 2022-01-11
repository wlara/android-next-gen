package com.github.wlara.nextgen.user.repo

import com.github.wlara.nextgen.user.model.User

interface UserRepository {
    suspend fun getUser(userId: Int): User
    suspend fun getAllUsers(): List<User>
}