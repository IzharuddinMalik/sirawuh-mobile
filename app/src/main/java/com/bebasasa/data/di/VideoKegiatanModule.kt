package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.VideoKegiatanRepositoryImpl
import com.bebasasa.data.repository.interfaces.VideoKegiatanRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.VideoKegiatanService
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
