package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.MuridHaidSiswaRepositoryImpl
import com.bebasasa.data.repository.interfaces.MuridHaidSiswaRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.murid.MuridHaidSiswaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MuridHaidModule {

    @Singleton
    @Provides
    fun provideMuridHaidRepository(
        service: MuridHaidSiswaService,
        preferenceHelper: PreferenceHelper
    ): MuridHaidSiswaRepository {
        return MuridHaidSiswaRepositoryImpl(service, preferenceHelper)
    }
}