package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.MuridKasKelasRepositoryImpl
import com.sirawuh.data.repository.interfaces.MuridKasKelasRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.murid.MuridKasKelasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MuridKasKelasModule {

    @Singleton
    @Provides
    fun provideMuridKasKelasRepository(
        service: MuridKasKelasService,
        preferenceHelper: PreferenceHelper
    ): MuridKasKelasRepository {
        return MuridKasKelasRepositoryImpl(service, preferenceHelper)
    }
}