package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.VideoKegiatanResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface VideoKegiatanRepository {

    fun buatVideoKegiatan(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getVideoKegiatan(): Flow<Result<VideoKegiatanResponse>>

    fun ubahVideoKegiatan(requestBody: RequestBody): Flow<Result<GeneralResponse>>
}
