package com.bebasasa.data.repository.implementation

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.KehadiranSiswaResponse
import com.bebasasa.data.domain.StatusKehadiranResponse
import com.bebasasa.data.repository.interfaces.MuridKehadiranRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.Result
import com.bebasasa.data.source.remote.service.murid.MuridKehadiranService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class MuridKehadiranRepositoryImpl(
    private val muridKehadiranService: MuridKehadiranService,
    private val preferenceHelper: PreferenceHelper
): MuridKehadiranRepository {
    override fun tambahKehadiran(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = muridKehadiranService.tambahKehadiran(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Tambah Kehadiran",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getStatusKehadiran(): Flow<Result<StatusKehadiranResponse>> = flow {
        val response = muridKehadiranService.getStatusKehadiran()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Status Kehadiran",
                body = "",
                error = error
            )
        )
    }

    override fun getPresensiKehadiran(requestBody: RequestBody): Flow<Result<KehadiranSiswaResponse>> = flow {
        val response = muridKehadiranService.getPresensiKehadiran(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }. catch { error ->
        emit(
            Result.createError(
                label = "Get Presensi Kehadiran",
                body = requestBody,
                error = error
            )
        )
    }
}
