package com.shivam.recipe.di

import com.shivam.recipe.data.repositoryImpl.FirestoreRepoImpl
import com.shivam.recipe.data.repositoryImpl.StorageRepoImpl
import com.shivam.recipe.domain.firebaseRepository.FirestoreRepo
import com.shivam.recipe.domain.firebaseRepository.StorageRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideFirestoreRepository(
        impl: FirestoreRepoImpl
    ): FirestoreRepo


    @Binds
    @Singleton
    abstract fun provideStorageRepository(
        impl: StorageRepoImpl
    ): StorageRepo
}