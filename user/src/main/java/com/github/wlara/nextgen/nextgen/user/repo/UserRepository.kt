package com.github.wlara.nextgen.nextgen.user.repo

import com.github.wlara.nextgen.nextgen.user.model.User

interface UserRepository {
    suspend fun getUser(userId: Int): User
    suspend fun getAllUsers(): List<User>
}