package com.sirawuh.data.di

import android.content.Context
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.AuthService
import com.sirawuh.data.source.remote.service.VideoKegiatanService
import com.sirawuh.data.source.remote.service.guru.GuruKelasService
import com.sirawuh.data.source.remote.service.guru.GuruPengumumanService
import com.sirawuh.data.source.remote.service.guru.ResumeService
import com.sirawuh.data.source.remote.service.murid.MuridHaidSiswaService
import com.sirawuh.data.source.remote.service.murid.MuridKasKelasService
import com.sirawuh.data.source.remote.service.murid.MuridKehadiranService
import com.sirawuh.data.source.remote.service.murid.MuridPiketKelasService
import com.sirawuh.utils.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context) = PreferenceHelper(context)

    @Singleton
    @Provides
    fun provideAuthService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): AuthService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = AuthService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideGuruKelasService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): GuruKelasService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = GuruKelasService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideGuruPengumumanService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): GuruPengumumanService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = GuruPengumumanService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideMuridHaidService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): MuridHaidSiswaService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = MuridHaidSiswaService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideMuridKasKelasService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): MuridKasKelasService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = MuridKasKelasService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideKehadiranService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): MuridKehadiranService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = MuridKehadiranService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideMuridPiketKelasService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): MuridPiketKelasService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = MuridPiketKelasService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideResumeService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): ResumeService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = ResumeService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideVideoKegiatanService(
        preferenceHelper: PreferenceHelper,
        @ApplicationContext context: Context
    ): VideoKegiatanService {
        return RetrofitBuilder.build(
            context = context,
            preferenceHelper = preferenceHelper,
            type = VideoKegiatanService::class.java
        )
    }
}
