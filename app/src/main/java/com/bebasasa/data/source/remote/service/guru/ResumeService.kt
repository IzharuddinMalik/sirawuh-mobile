package com.bebasasa.data.source.remote.service.guru

import com.bebasasa.data.domain.ResumeHaidResponse
import com.bebasasa.data.domain.ResumeKasResponse
import com.bebasasa.data.domain.ResumeKehadiranResponse
import com.bebasasa.data.domain.ResumePiketResponse
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
