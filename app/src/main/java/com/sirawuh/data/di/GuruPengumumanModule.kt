package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.GuruPengumumanRepositoryImpl
import com.sirawuh.data.repository.interfaces.GuruPengumumanRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.guru.GuruPengumumanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GuruPengumumanModule {

    @Singleton
    @Provides
    fun provideGuruPengumumanRepository(
        service: GuruPengumumanService,
        preferenceHelper: PreferenceHelper
    ): GuruPengumumanRepository {
        return GuruPengumumanRepositoryImpl(service, preferenceHelper)
    }
}