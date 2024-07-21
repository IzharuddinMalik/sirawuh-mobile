package com.bebasasa.data.repository.implementation

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.KasKelasSiswaResponse
import com.bebasasa.data.repository.interfaces.MuridKasKelasRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.Result
import com.bebasasa.data.source.remote.service.murid.MuridKasKelasService
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
