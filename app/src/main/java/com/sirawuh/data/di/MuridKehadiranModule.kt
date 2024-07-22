package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.MuridKehadiranRepositoryImpl
import com.sirawuh.data.repository.interfaces.MuridKehadiranRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.murid.MuridKehadiranService
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
    ): MuridKehadiranRepository {
        return MuridKehadiranRepositoryImpl(service, preferenceHelper)
    }
}