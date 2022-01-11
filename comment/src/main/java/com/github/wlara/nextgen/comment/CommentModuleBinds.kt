package com.github.wlara.nextgen.comment

import com.github.wlara.nextgen.comment.repo.impl.CommentRepositoryImpl
import com.github.wlara.nextgen.comment.network.CommentService
import com.github.wlara.nextgen.comment.network.impl.CommentServiceImpl
import com.github.wlara.nextgen.comment.repo.CommentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class CommentModuleBinds {

    @Binds
    abstract fun bindCommentRepository(repo: CommentRepositoryImpl): CommentRepository

    @Binds
    abstract fun bindCommentService(service: CommentServiceImpl): CommentService
}