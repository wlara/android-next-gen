package com.github.wlara.nextgen.post.network

import com.github.wlara.nextgen.post.model.Post

interface PostService {
    suspend fun getPost(postId: Int): Post
    suspend fun getAllPosts(): List<Post>
    suspend fun getUserPosts(userId: Int): List<Post>
}