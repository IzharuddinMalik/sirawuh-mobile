package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.ResumeRepositoryImpl
import com.sirawuh.data.repository.interfaces.ResumeRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.guru.ResumeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResumeModule {

    @Singleton
    @Provides
    fun provideResumeRepository(
        service: ResumeService,
        preferenceHelper: PreferenceHelper
    ): ResumeRepository {
        return ResumeRepositoryImpl(service, preferenceHelper)
    }
}
