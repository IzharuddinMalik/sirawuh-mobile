package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.PengumumanResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface GuruPengumumanRepository {

    fun buatPengumuman(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getPengumuman(): Flow<Result<PengumumanResponse>>

    fun ubahPengumuman(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun hapusPengumuman(requestBody: RequestBody): Flow<Result<GeneralResponse>>
}
