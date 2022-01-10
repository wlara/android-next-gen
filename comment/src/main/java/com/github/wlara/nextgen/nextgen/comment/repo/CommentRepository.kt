package com.github.wlara.nextgen.nextgen.comment.repo

import com.github.wlara.nextgen.nextgen.comment.model.Comment

interface CommentRepository {
    suspend fun getComment(commentId: Int): Comment
    suspend fun getAllComments(): List<Comment>
    suspend fun getPostComments(postId: Int): List<Comment>
}