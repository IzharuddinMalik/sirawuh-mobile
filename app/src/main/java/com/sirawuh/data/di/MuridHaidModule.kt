package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.MuridHaidSiswaRepositoryImpl
import com.sirawuh.data.repository.interfaces.MuridHaidSiswaRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.murid.MuridHaidSiswaService
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