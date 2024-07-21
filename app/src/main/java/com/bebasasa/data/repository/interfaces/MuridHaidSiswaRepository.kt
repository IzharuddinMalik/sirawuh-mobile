package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.HaidSiswaResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface MuridHaidSiswaRepository {
    fun tambahInfoHaid(requestBody: RequestBody): Flow<Result<GeneralResponse>>

    fun getInfoHaid(requestBody: RequestBody): Flow<Result<HaidSiswaResponse>>
}
