package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.ResumeHaidResponse
import com.bebasasa.data.domain.ResumeKasResponse
import com.bebasasa.data.domain.ResumeKehadiranResponse
import com.bebasasa.data.domain.ResumePiketResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow

interface ResumeRepository {

    fun getResumeHaid(): Flow<Result<ResumeHaidResponse>>

    fun getResumeKehadiran(): Flow<Result<ResumeKehadiranResponse>>

    fun getResumePiket(): Flow<Result<ResumePiketResponse>>

    fun getResumeKas(): Flow<Result<ResumeKasResponse>>
}
