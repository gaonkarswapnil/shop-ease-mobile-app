package com.example.shopease.di

import com.example.shopease.repository.implementation.CategoriesRepositoryImpl
import com.example.shopease.repository.implementation.LoginRepositoryImpl
import com.example.shopease.repository.implementation.UserRepositoryImpl
import com.example.shopease.repository.interfaces.CategoriesRepository
import com.example.shopease.repository.interfaces.LoginRepository
import com.example.shopease.repository.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    @Singleton
    fun provideUserModule(user: UserRepositoryImpl): UserRepository{
        return user
    }

    @Provides
    @Singleton
    fun provideLoginModule(user: LoginRepositoryImpl): LoginRepository{
        return user
    }

    @Provides
    @Singleton
    fun provideCategoryModule(category: CategoriesRepositoryImpl): CategoriesRepository {
        return category
    }
}