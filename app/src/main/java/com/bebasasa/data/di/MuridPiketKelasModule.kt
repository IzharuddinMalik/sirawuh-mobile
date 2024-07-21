package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.MuridPiketKelasRepositoryImpl
import com.bebasasa.data.repository.interfaces.MuridPiketKelasRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.murid.MuridPiketKelasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MuridPiketKelasModule {

    @Singleton
    @Provides
    fun provideMuridPiketKelasRepository(
        service: MuridPiketKelasService,
        preferenceHelper: PreferenceHelper
    ): MuridPiketKelasRepository {
        return MuridPiketKelasRepositoryImpl(service, preferenceHelper)
    }
}
