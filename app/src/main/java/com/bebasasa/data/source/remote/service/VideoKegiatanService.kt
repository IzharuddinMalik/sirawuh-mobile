package com.bebasasa.data.source.remote.service

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.VideoKegiatanResponse
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
