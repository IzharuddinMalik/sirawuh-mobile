package com.bebasasa.data.repository.implementation

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.PengumumanResponse
import com.bebasasa.data.repository.interfaces.GuruPengumumanRepository
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.service.Result
import com.bebasasa.data.source.remote.service.guru.GuruPengumumanService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class GuruPengumumanRepositoryImpl(
    private val pengumumanService: GuruPengumumanService,
    private val preferenceHelper: PreferenceHelper
): GuruPengumumanRepository {

    override fun buatPengumuman(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = pengumumanService.buatPengumuman(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Buat Pengumuman",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getPengumuman(): Flow<Result<PengumumanResponse>> = flow {
        val response = pengumumanService.getPengumuman()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Pengumuman",
                body = "",
                error = error
            )
        )
    }

    override fun ubahPengumuman(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = pengumumanService.ubahPengumuman(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Ubah Pengumuman",
                body = requestBody,
                error = error
            )
        )
    }

    override fun hapusPengumuman(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = pengumumanService.hapusPengumuman(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "hapus Pengumuman",
                body = requestBody,
                error = error
            )
        )
    }
}