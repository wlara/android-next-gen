package com.github.wlara.nextgen.post.repo

import com.github.wlara.nextgen.post.model.Post

interface PostRepository {
    suspend fun getPost(postId: Int): Post
    suspend fun getAllPosts(): List<Post>
    suspend fun getUserPosts(userId: Int): List<Post>
}