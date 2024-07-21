package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.GuruPengumumanRepositoryImpl
import com.bebasasa.data.repository.interfaces.GuruPengumumanRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.guru.GuruPengumumanService
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