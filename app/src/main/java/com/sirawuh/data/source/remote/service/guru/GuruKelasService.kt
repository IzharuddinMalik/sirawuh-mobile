package com.sirawuh.data.source.remote.service.guru

import com.sirawuh.data.domain.GuruKasSiswaResponse
import com.sirawuh.data.domain.GuruKehadiranSiswaResponse
import com.sirawuh.data.domain.GuruPiketSiswaResponse
import com.sirawuh.data.domain.GuruSiswaHaidResponse
import com.sirawuh.data.domain.ListSiswaResponse
import com.sirawuh.data.domain.TanggalKasResponse
import com.sirawuh.data.domain.TanggalKehadiranResponse
import com.sirawuh.data.domain.TanggalPiketResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GuruKelasService {

    @GET("guru/kelas/getsiswa")
    suspend fun getSiswaKelas() : ListSiswaResponse

    @GET("guru/kelas/gettanggalkehadiransiswa")
    suspend fun getTanggalKehadiranSiswa(): TanggalKehadiranResponse

    @POST("guru/kelas/getkehadiransiswa")
    suspend fun getKehadiranSiswaKelas(@Body requestBody: RequestBody): GuruKehadiranSiswaResponse

    @GET("guru/kelas/getsiswahaid")
    suspend fun getSiswaHaidKelas(): GuruSiswaHaidResponse

    @GET("guru/kelas/gettanggalpiket")
    suspend fun getTanggalPiketKelas(): TanggalPiketResponse

    @POST("guru/kelas/getpiketkelas")
    suspend fun getPiketKelas(@Body requestBody: RequestBody): GuruPiketSiswaResponse

    @GET("guru/kelas/gettanggalbayarkas")
    suspend fun getTanggalBayarKas(): TanggalKasResponse

    @POST("guru/kelas/getbayarkaskelas")
    suspend fun getBayarKasKelas(@Body requestBody: RequestBody): GuruKasSiswaResponse
}