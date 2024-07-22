package com.sirawuh.data.source.remote.service

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.VideoKegiatanResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VideoKegiatanService {

    @POST("videokegiatan/buatvideokegiatan")
    suspend fun buatVideoKegiatan(@Body requestBody: RequestBody): GeneralResponse

    @GET("videokegiatan/getvideokegiatan")
    suspend fun getVideoKegiatan(): VideoKegiatanResponse

    @POST("videokegiatan/ubahvideokegiatan")
    suspend fun ubahVideoKegiatan(@Body requestBody: RequestBody): GeneralResponse
}
