package com.bebasasa.data.source.remote.service.murid

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.KasKelasSiswaResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MuridKasKelasService {

    @POST("murid/kaskelas/tambahkaskelas")
    suspend fun tambahKasKelas(@Body requestBody: RequestBody): GeneralResponse

    @POST("murid/kaskelas/getinfokaskelas")
    suspend fun getInfoKasKelas(@Body requestBody: RequestBody): KasKelasSiswaResponse
}
