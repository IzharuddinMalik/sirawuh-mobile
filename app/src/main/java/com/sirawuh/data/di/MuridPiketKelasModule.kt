package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.MuridPiketKelasRepositoryImpl
import com.sirawuh.data.repository.interfaces.MuridPiketKelasRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.murid.MuridPiketKelasService
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
