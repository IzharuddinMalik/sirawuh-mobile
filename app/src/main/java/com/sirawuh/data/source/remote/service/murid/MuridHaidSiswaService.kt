package com.sirawuh.data.source.remote.service.murid

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.HaidSiswaResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MuridHaidSiswaService {

    @POST("murid/haidsiswa/tambahhaid")
    suspend fun tambahInfoHaid(@Body requestBody: RequestBody): GeneralResponse

    @POST("murid/haidsiswa/getinformasihaid")
    suspend fun getInfoHaid(@Body requestBody: RequestBody): HaidSiswaResponse
}
