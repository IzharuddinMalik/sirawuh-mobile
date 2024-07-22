package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.VideoKegiatanResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface VideoKegiatanRepository {

    fun buatVideoKegiatan(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getVideoKegiatan(): Flow<Result<VideoKegiatanResponse>>

    fun ubahVideoKegiatan(requestBody: RequestBody): Flow<Result<GeneralResponse>>
}
