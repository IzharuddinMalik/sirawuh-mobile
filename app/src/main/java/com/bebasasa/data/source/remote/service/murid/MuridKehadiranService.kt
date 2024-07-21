package com.bebasasa.data.source.remote.service.murid

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.KehadiranSiswaResponse
import com.bebasasa.data.domain.StatusKehadiranResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MuridKehadiranService {

    @POST("murid/kehadiran/tambahkehadiran")
    suspend fun tambahKehadiran(@Body requestBody: RequestBody): GeneralResponse

    @GET("murid/kehadiran/getstatuskehadiran")
    suspend fun getStatusKehadiran(): StatusKehadiranResponse

    @POST("murid/kehadiran/getpresensikehadiran")
    suspend fun getPresensiKehadiran(@Body requestBody: RequestBody): KehadiranSiswaResponse
}
