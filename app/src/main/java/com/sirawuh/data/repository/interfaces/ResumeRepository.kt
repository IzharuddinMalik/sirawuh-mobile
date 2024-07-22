package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.ResumeHaidResponse
import com.sirawuh.data.domain.ResumeKasResponse
import com.sirawuh.data.domain.ResumeKehadiranResponse
import com.sirawuh.data.domain.ResumePiketResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow

interface ResumeRepository {

    fun getResumeHaid(): Flow<Result<ResumeHaidResponse>>

    fun getResumeKehadiran(): Flow<Result<ResumeKehadiranResponse>>

    fun getResumePiket(): Flow<Result<ResumePiketResponse>>

    fun getResumeKas(): Flow<Result<ResumeKasResponse>>
}
