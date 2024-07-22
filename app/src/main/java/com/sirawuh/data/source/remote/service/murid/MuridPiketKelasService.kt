package com.sirawuh.data.source.remote.service.murid

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.PiketSiswaResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MuridPiketKelasService {

    @POST("murid/piketkelas/tambahpiket")
    suspend fun tambahPiketKelas(@Body requestBody: RequestBody): GeneralResponse

    @POST("murid/piketkelas/getinfopiket")
    suspend fun getInfoPiketKelas(@Body requestBody: RequestBody): PiketSiswaResponse
}
