package com.sirawuh.data.repository.implementation

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.VideoKegiatanResponse
import com.sirawuh.data.repository.interfaces.VideoKegiatanRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import com.sirawuh.data.source.remote.service.VideoKegiatanService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class VideoKegiatanRepositoryImpl(
    private val videoKegiatanService: VideoKegiatanService,
    private val preferenceHelper: PreferenceHelper
): VideoKegiatanRepository {
    override fun buatVideoKegiatan(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = videoKegiatanService.buatVideoKegiatan(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Buat Video Kegiatan",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getVideoKegiatan(): Flow<Result<VideoKegiatanResponse>> = flow {
        val response = videoKegiatanService.getVideoKegiatan()
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Video Kegiatan",
                body = "",
                error = error
            )
        )
    }

    override fun ubahVideoKegiatan(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = videoKegiatanService.ubahVideoKegiatan(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Ubah Video Kegiatan",
                body = requestBody,
                error = error
            )
        )
    }
}
