package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.PiketSiswaResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridPiketKelasRepository {

    fun tambahPiketKelas(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getInfoPiketKelas(requestBody: RequestBody): Flow<Result<PiketSiswaResponse>>
}
