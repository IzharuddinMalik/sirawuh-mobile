package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.VideoKegiatanRepositoryImpl
import com.sirawuh.data.repository.interfaces.VideoKegiatanRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.VideoKegiatanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoKegiatanModule {

    @Singleton
    @Provides
    fun provideVideoKegiatanRepository(
        service: VideoKegiatanService,
        preferenceHelper: PreferenceHelper
    ): VideoKegiatanRepository {
        return VideoKegiatanRepositoryImpl(service, preferenceHelper)
    }
}
