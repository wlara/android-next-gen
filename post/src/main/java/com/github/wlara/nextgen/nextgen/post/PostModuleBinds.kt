package com.github.wlara.nextgen.nextgen.post

import com.github.wlara.nextgen.nextgen.post.network.PostService
import com.github.wlara.nextgen.nextgen.post.network.impl.PostServiceImpl
import com.github.wlara.nextgen.nextgen.post.repo.PostRepository
import com.github.wlara.nextgen.nextgen.post.repo.impl.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class PostModuleBinds {

    @Binds
    abstract fun bindPostRepository(repo: PostRepositoryImpl): PostRepository

    @Binds
    abstract fun bindPostService(service: PostServiceImpl): PostService
}