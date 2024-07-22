package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.KasKelasSiswaResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridKasKelasRepository {
    fun tambahKasKelas(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getInfoKasKelas(requestBody: RequestBody): Flow<Result<KasKelasSiswaResponse>>
}
