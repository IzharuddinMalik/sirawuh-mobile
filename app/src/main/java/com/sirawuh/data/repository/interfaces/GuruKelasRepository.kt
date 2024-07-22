package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.GuruKasSiswaResponse
import com.sirawuh.data.domain.GuruKehadiranSiswaResponse
import com.sirawuh.data.domain.GuruPiketSiswaResponse
import com.sirawuh.data.domain.GuruSiswaHaidResponse
import com.sirawuh.data.domain.ListSiswaResponse
import com.sirawuh.data.domain.TanggalKasResponse
import com.sirawuh.data.domain.TanggalKehadiranResponse
import com.sirawuh.data.domain.TanggalPiketResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface GuruKelasRepository {

    fun getSiswaKelas() : Flow<Result<ListSiswaResponse>>

    fun getTanggalKehadiranSiswa(): Flow<Result<TanggalKehadiranResponse>>

    fun getKehadiranSiswaKelas(requestBody: RequestBody): Flow<Result<GuruKehadiranSiswaResponse>>

    fun getSiswaHaidKelas(): Flow<Result<GuruSiswaHaidResponse>>

    fun getTanggalPiketKelas(): Flow<Result<TanggalPiketResponse>>

    fun getPiketKelas(requestBody: RequestBody): Flow<Result<GuruPiketSiswaResponse>>

    fun getTanggalBayarKas(): Flow<Result<TanggalKasResponse>>

    fun getBayarKasKelas(requestBody: RequestBody): Flow<Result<GuruKasSiswaResponse>>
}
