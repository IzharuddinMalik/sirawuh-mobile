package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.GuruKelasRepositoryImpl
import com.bebasasa.data.repository.interfaces.GuruKelasRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.guru.GuruKelasService
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
    ): GuruKelasRepository{
        return GuruKelasRepositoryImpl(service, preferenceHelper)
    }
}