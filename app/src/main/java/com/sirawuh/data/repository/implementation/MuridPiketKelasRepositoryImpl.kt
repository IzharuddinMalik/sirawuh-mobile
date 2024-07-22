package com.sirawuh.data.repository.implementation

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.PiketSiswaResponse
import com.sirawuh.data.repository.interfaces.MuridPiketKelasRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import com.sirawuh.data.source.remote.service.murid.MuridPiketKelasService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class MuridPiketKelasRepositoryImpl(
    private val muridPiketKelasService: MuridPiketKelasService,
    private val preferenceHelper: PreferenceHelper
): MuridPiketKelasRepository {
    override fun tambahPiketKelas(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = muridPiketKelasService.tambahPiketKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Tambah Piket Kelas",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getInfoPiketKelas(requestBody: RequestBody): Flow<Result<PiketSiswaResponse>> = flow {
        val response = muridPiketKelasService.getInfoPiketKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Info Piket Kelas",
                body = requestBody,
                error = error
            )
        )
    }
}
