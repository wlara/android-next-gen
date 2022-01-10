package com.github.wlara.nextgen.nextgen.comment.repo.impl

import com.github.wlara.nextgen.nextgen.comment.network.CommentService
import com.github.wlara.nextgen.nextgen.comment.model.Comment
import com.github.wlara.nextgen.nextgen.comment.repo.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class CommentRepositoryImpl @Inject constructor(
    private val service: CommentService
) : CommentRepository {

    override suspend fun getComment(commentId: Int): Comment = withContext(Dispatchers.IO) {
        //delay(3000)
        service.getComment(commentId)
    }

    override suspend fun getAllComments(): List<Comment> = withContext(Dispatchers.IO) {
       /// delay(3000)
        service.getAllComments().sortedBy { it.id }
    }


    override suspend fun getPostComments(postId: Int): List<Comment>  = withContext(Dispatchers.IO) {
        /// delay(3000)
        service.getPostComments(postId).sortedBy { it.id }
    }
}