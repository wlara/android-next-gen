package com.github.wlara.nextgen.post.network.impl

import com.github.wlara.nextgen.post.BuildConfig.BASE_API_URL
import com.github.wlara.nextgen.post.model.Post
import com.github.wlara.nextgen.post.network.PostService
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

internal class PostServiceImpl @Inject constructor(
    private val client: HttpClient
) : PostService {
    override suspend fun getPost(postId: Int): Post =
        client.get("$BASE_API_URL/posts/$postId")

    override suspend fun getAllPosts(): List<Post> =
        client.get("$BASE_API_URL/posts")

    override suspend fun getUserPosts(userId: Int): List<Post> =
        client.get("$BASE_API_URL/posts") {
            parameter("userId", userId)
        }
}