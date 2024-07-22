package com.sirawuh.data.source.remote.service.murid

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.KehadiranSiswaResponse
import com.sirawuh.data.domain.StatusKehadiranResponse
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
