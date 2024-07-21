package com.bebasasa.data.repository.implementation

import com.bebasasa.data.domain.ResumeHaidResponse
import com.bebasasa.data.domain.ResumeKasResponse
import com.bebasasa.data.domain.ResumeKehadiranResponse
import com.bebasasa.data.domain.ResumePiketResponse
import com.bebasasa.data.repository.interfaces.ResumeRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.Result
import com.bebasasa.data.source.remote.service.guru.ResumeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ResumeRepositoryImpl(
    private val resumeService: ResumeService,
    private val preferenceHelper: PreferenceHelper
): ResumeRepository {
    override fun getResumeHaid(): Flow<Result<ResumeHaidResponse>> = flow {
        val response = resumeService.getResumeHaid()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
            label = "Get Resume Haid",
            body = "",
            error = error
        ))
    }

    override fun getResumeKehadiran(): Flow<Result<ResumeKehadiranResponse>> = flow {
        val response = resumeService.getResumeKehadiran()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Resume Kehadiran",
                body = "",
                error = error
            )
        )
    }

    override fun getResumePiket(): Flow<Result<ResumePiketResponse>> = flow {
        val response = resumeService.getResumePiket()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Resume Piket",
                body = "",
                error = error
            )
        )
    }

    override fun getResumeKas(): Flow<Result<ResumeKasResponse>> = flow {
        val response = resumeService.getResumeKas()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Resume Kas",
                body = "",
                error = error
            )
        )
    }
}
