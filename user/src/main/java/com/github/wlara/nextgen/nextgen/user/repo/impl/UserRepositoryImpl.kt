package com.github.wlara.nextgen.nextgen.user.repo.impl

import com.github.wlara.nextgen.nextgen.user.model.User
import com.github.wlara.nextgen.nextgen.user.network.UserService
import com.github.wlara.nextgen.nextgen.user.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val service: UserService
) : UserRepository {

    override suspend fun getUser(userId: Int): User = withContext(Dispatchers.IO) {
        //delay(3000)
        service.getUser(userId)
    }

    override suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
       /// delay(3000)
        service.getAllUsers().sortedBy { it.name }
    }
}