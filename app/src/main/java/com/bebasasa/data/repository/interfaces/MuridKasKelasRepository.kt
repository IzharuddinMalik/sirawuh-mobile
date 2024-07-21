package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.KasKelasSiswaResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridKasKelasRepository {
    fun tambahKasKelas(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getInfoKasKelas(requestBody: RequestBody): Flow<Result<KasKelasSiswaResponse>>
}
