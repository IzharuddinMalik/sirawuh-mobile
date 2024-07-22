package com.sirawuh.data.di

import com.sirawuh.data.repository.implementation.AuthRepositoryImpl
import com.sirawuh.data.repository.interfaces.AuthRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.AuthService
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
