package com.github.wlara.nextgen.post.repo.impl

import com.github.wlara.nextgen.post.model.Post
import com.github.wlara.nextgen.post.network.PostService
import com.github.wlara.nextgen.post.repo.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PostRepositoryImpl @Inject constructor(
    private val service: PostService
) : PostRepository {

    override suspend fun getPost(postId: Int): Post = withContext(Dispatchers.IO) {
        service.getPost(postId)
    }

    override suspend fun getAllPosts(): List<Post> = withContext(Dispatchers.IO) {
        service.getAllPosts().sortedBy { it.id }
    }


    override suspend fun getUserPosts(userId: Int): List<Post>  = withContext(Dispatchers.IO) {
        service.getUserPosts(userId).sortedBy { it.id }
    }
}