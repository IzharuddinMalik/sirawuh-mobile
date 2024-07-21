package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.KehadiranSiswaResponse
import com.bebasasa.data.domain.StatusKehadiranResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridKehadiranRepository {

    fun tambahKehadiran(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getStatusKehadiran(): Flow<Result<StatusKehadiranResponse>>

    fun getPresensiKehadiran(requestBody: RequestBody): Flow<Result<KehadiranSiswaResponse>>
}
