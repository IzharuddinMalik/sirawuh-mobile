package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.PiketSiswaResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridPiketKelasRepository {

    fun tambahPiketKelas(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getInfoPiketKelas(requestBody: RequestBody): Flow<Result<PiketSiswaResponse>>
}
