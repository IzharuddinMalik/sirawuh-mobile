package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.MuridKasKelasRepositoryImpl
import com.bebasasa.data.repository.interfaces.MuridKasKelasRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.murid.MuridKasKelasService
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
    ): MuridKasKelasRepository{
        return MuridKasKelasRepositoryImpl(service, preferenceHelper)
    }
}