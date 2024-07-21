package com.bebasasa.data.source.remote.service.murid

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.PiketSiswaResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MuridPiketKelasService {

    @POST("murid/piketkelas/tambahpiket")
    suspend fun tambahPiketKelas(@Body requestBody: RequestBody): GeneralResponse

    @POST("murid/piketkelas/getinfopiket")
    suspend fun getInfoPiketKelas(@Body requestBody: RequestBody): PiketSiswaResponse
}
