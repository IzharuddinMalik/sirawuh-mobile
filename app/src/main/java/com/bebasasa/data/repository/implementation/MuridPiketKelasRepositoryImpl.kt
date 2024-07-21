package com.bebasasa.data.repository.implementation

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.PiketSiswaResponse
import com.bebasasa.data.repository.interfaces.MuridPiketKelasRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.Result
import com.bebasasa.data.source.remote.service.murid.MuridPiketKelasService
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
