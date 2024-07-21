package com.bebasasa.data.di

import com.bebasasa.data.repository.implementation.AuthRepositoryImpl
import com.bebasasa.data.repository.interfaces.AuthRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        service: AuthService,
        preferenceHelper: PreferenceHelper
    ): AuthRepository {
        return AuthRepositoryImpl(service, preferenceHelper)
    }
}
