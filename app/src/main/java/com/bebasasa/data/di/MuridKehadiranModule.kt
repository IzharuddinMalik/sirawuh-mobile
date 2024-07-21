package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.MuridKehadiranRepositoryImpl
import com.bebasasa.data.repository.interfaces.MuridKehadiranRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.murid.MuridKehadiranService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MuridKehadiranModule {

    @Singleton
    @Provides
    fun provideMuridKehadiranRepository(
        service: MuridKehadiranService,
        preferenceHelper: PreferenceHelper
    ): MuridKehadiranRepository{
        return MuridKehadiranRepositoryImpl(service, preferenceHelper)
    }
}