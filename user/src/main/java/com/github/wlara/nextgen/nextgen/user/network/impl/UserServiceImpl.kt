package com.github.wlara.nextgen.nextgen.user.network.impl

import com.github.wlara.nextgen.nextgen.user.BuildConfig.BASE_API_URL
import com.github.wlara.nextgen.nextgen.user.model.User
import com.github.wlara.nextgen.nextgen.user.network.UserService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

internal class UserServiceImpl @Inject constructor(
    private val client: HttpClient
) : UserService {
    override suspend fun getUser(userId: Int): User =
        client.get("$BASE_API_URL/users/$userId")

    override suspend fun getAllUsers(): List<User> =
        client.get("$BASE_API_URL/users")
}