package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.HaidSiswaResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridHaidSiswaRepository {
    fun tambahInfoHaid(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getInfoHaid(requestBody: RequestBody): Flow<Result<HaidSiswaResponse>>
}
