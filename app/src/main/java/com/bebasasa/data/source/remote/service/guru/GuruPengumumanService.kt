package com.bebasasa.data.source.remote.service.guru

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.PengumumanResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GuruPengumumanService {

    @POST("guru/papaninformasi/buatpengumuman")
    suspend fun buatPengumuman(@Body requestBody: RequestBody): GeneralResponse

    @GET("guru/papaninformasi/getpengumuman")
    suspend fun getPengumuman(): PengumumanResponse

    @POST("guru/papaninformasi/ubahpengumuman")
    suspend fun ubahPengumuman(@Body requestBody: RequestBody): GeneralResponse

    @POST("guru/papaninformasi/hapuspengumuman")
    suspend fun hapusPengumuman(@Body requestBody: RequestBody): GeneralResponse
}