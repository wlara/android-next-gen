package com.github.wlara.nextgen.user

import com.github.wlara.nextgen.user.network.UserService
import com.github.wlara.nextgen.user.network.impl.UserServiceImpl
import com.github.wlara.nextgen.user.repo.UserRepository
import com.github.wlara.nextgen.user.repo.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UserModuleBinds {

    @Binds
    abstract fun bindUserRepository(repo: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindUserService(service: UserServiceImpl): UserService
}