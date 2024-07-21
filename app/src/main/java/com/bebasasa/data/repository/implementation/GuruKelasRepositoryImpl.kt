package com.bebasasa.data.repository.implementation

import com.bebasasa.data.domain.GuruKasSiswaResponse
import com.bebasasa.data.domain.GuruKehadiranSiswaResponse
import com.bebasasa.data.domain.GuruPiketSiswaResponse
import com.bebasasa.data.domain.GuruSiswaHaidResponse
import com.bebasasa.data.domain.ListSiswaResponse
import com.bebasasa.data.domain.TanggalKasResponse
import com.bebasasa.data.domain.TanggalKehadiranResponse
import com.bebasasa.data.domain.TanggalPiketResponse
import com.bebasasa.data.repository.interfaces.GuruKelasRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.Result
import com.bebasasa.data.source.remote.service.guru.GuruKelasService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class GuruKelasRepositoryImpl(
    private val guruKelasService: GuruKelasService,
    private val preferenceHelper: PreferenceHelper
): GuruKelasRepository {

    override fun getSiswaKelas(): Flow<Result<ListSiswaResponse>> = flow {
        val response = guruKelasService.getSiswaKelas()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Siswa Kelas",
                body = "",
                error = error
            )
        )
    }

    override fun getTanggalKehadiranSiswa(): Flow<Result<TanggalKehadiranResponse>> = flow {
        val response = guruKelasService.getTanggalKehadiranSiswa()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(Result.createError(
            label = "Get Tanggal Kehadiran Siswa",
            body = "",
            error = error
        ))
    }

    override fun getKehadiranSiswaKelas(requestBody: RequestBody): Flow<Result<GuruKehadiranSiswaResponse>> = flow {
        val response = guruKelasService.getKehadiranSiswaKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Kehadiran Siswa",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getSiswaHaidKelas(): Flow<Result<GuruSiswaHaidResponse>> = flow {
        val response = guruKelasService.getSiswaHaidKelas()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Siswa Haid",
                body = "",
                error = error
            )
        )
    }

    override fun getTanggalPiketKelas(): Flow<Result<TanggalPiketResponse>> = flow {
        val response = guruKelasService.getTanggalPiketKelas()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Tanggal Piket",
                body = "",
                error = error
            )
        )
    }

    override fun getPiketKelas(requestBody: RequestBody): Flow<Result<GuruPiketSiswaResponse>> = flow {
        val response = guruKelasService.getPiketKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Piket Kelas",
                body = "",
                error = error
            )
        )
    }

    override fun getTanggalBayarKas(): Flow<Result<TanggalKasResponse>> = flow {
        val response = guruKelasService.getTanggalBayarKas()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Tanggal Bayar Kas",
                body = "",
                error = error
            )
        )
    }

    override fun getBayarKasKelas(requestBody: RequestBody): Flow<Result<GuruKasSiswaResponse>> = flow {
        val response = guruKelasService.getBayarKasKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(Result.createError(
            label = "Get Bayar Kas Kelas",
            body = requestBody,
            error = error
        ))
    }
}