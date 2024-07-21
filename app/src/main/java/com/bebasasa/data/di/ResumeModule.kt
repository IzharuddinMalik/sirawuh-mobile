package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.ResumeRepositoryImpl
import com.bebasasa.data.repository.interfaces.ResumeRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.guru.ResumeService
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
