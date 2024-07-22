package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.KehadiranSiswaResponse
import com.sirawuh.data.domain.StatusKehadiranResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridKehadiranRepository {

    fun tambahKehadiran(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getStatusKehadiran(): Flow<Result<StatusKehadiranResponse>>

    fun getPresensiKehadiran(requestBody: RequestBody): Flow<Result<KehadiranSiswaResponse>>
}
