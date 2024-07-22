package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.GuruKelasRepositoryImpl
import com.sirawuh.data.repository.interfaces.GuruKelasRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.guru.GuruKelasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GuruKelasModule {

    @Singleton
    @Provides
    fun provideGuruKelasRepository(
        service: GuruKelasService,
        preferenceHelper: PreferenceHelper
    ): GuruKelasRepository {
        return GuruKelasRepositoryImpl(service, preferenceHelper)
    }
}