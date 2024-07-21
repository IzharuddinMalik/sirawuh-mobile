package com.bebasasa.data.source.remote.service.murid

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.HaidSiswaResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MuridHaidSiswaService {

    @POST("murid/haidsiswa/tambahhaid")
    suspend fun tambahInfoHaid(@Body requestBody: RequestBody): GeneralResponse

    @POST("murid/haidsiswa/getinformasihaid")
    suspend fun getInfoHaid(@Body requestBody: RequestBody): HaidSiswaResponse
}
