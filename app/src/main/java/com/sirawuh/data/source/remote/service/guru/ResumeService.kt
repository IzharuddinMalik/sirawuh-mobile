package com.sirawuh.data.source.remote.service.guru

import com.sirawuh.data.domain.ResumeHaidResponse
import com.sirawuh.data.domain.ResumeKasResponse
import com.sirawuh.data.domain.ResumeKehadiranResponse
import com.sirawuh.data.domain.ResumePiketResponse
import retrofit2.http.GET

interface ResumeService {

    @GET("resume/getresumehaid")
    suspend fun getResumeHaid(): ResumeHaidResponse

    @GET("resume/getresumekehadiran")
    suspend fun getResumeKehadiran(): ResumeKehadiranResponse

    @GET("resume/getresumepiket")
    suspend fun getResumePiket(): ResumePiketResponse

    @GET("resume/getresumekas")
    suspend fun getResumeKas(): ResumeKasResponse
}
