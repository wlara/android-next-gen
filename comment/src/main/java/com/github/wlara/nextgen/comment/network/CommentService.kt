package com.github.wlara.nextgen.comment.network

import com.github.wlara.nextgen.comment.model.Comment

interface CommentService {
    suspend fun getComment(commentId: Int): Comment
    suspend fun getAllComments(): List<Comment>
    suspend fun getPostComments(postId: Int): List<Comment>
}