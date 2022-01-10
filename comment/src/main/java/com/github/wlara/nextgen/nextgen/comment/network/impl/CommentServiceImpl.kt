package com.github.wlara.nextgen.nextgen.comment.network.impl

import com.github.wlara.nextgen.nextgen.comment.BuildConfig.BASE_API_URL
import com.github.wlara.nextgen.nextgen.comment.model.Comment
import com.github.wlara.nextgen.nextgen.comment.network.CommentService
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

internal class CommentServiceImpl @Inject constructor(
    private val client: HttpClient
) : CommentService {
    override suspend fun getComment(commentId: Int): Comment =
        client.get("$BASE_API_URL/comments/$commentId")

    override suspend fun getAllComments(): List<Comment> =
        client.get("$BASE_API_URL/comments")

    override suspend fun getPostComments(postId: Int): List<Comment> =
        client.get("$BASE_API_URL/comments") {
            parameter("postId", postId)
        }
}