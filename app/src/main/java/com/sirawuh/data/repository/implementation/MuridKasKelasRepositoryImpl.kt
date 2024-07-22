package com.sirawuh.data.repository.implementation

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.KasKelasSiswaResponse
import com.sirawuh.data.repository.interfaces.MuridKasKelasRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import com.sirawuh.data.source.remote.service.murid.MuridKasKelasService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class MuridKasKelasRepositoryImpl(
    private val muridKasKelasService: MuridKasKelasService,
    private val preferenceHelper: PreferenceHelper
): MuridKasKelasRepository {
    override fun tambahKasKelas(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = muridKasKelasService.tambahKasKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Tambah Kas Kelas",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getInfoKasKelas(requestBody: RequestBody): Flow<Result<KasKelasSiswaResponse>> = flow {
        val response = muridKasKelasService.getInfoKasKelas(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Info Kas Kelas",
                body = requestBody,
                error = error
            )
        )
    }
}
