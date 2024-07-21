package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GuruKasSiswaResponse
import com.bebasasa.data.domain.GuruKehadiranSiswaResponse
import com.bebasasa.data.domain.GuruPiketSiswaResponse
import com.bebasasa.data.domain.GuruSiswaHaidResponse
import com.bebasasa.data.domain.ListSiswaResponse
import com.bebasasa.data.domain.TanggalKasResponse
import com.bebasasa.data.domain.TanggalKehadiranResponse
import com.bebasasa.data.domain.TanggalPiketResponse
import com.bebasasa.data.source.remote.service.Result
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
