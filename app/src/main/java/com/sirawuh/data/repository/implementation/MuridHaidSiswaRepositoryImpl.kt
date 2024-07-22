package com.sirawuh.data.repository.implementation

import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.HaidSiswaResponse
import com.sirawuh.data.repository.interfaces.MuridHaidSiswaRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import com.sirawuh.data.source.remote.service.murid.MuridHaidSiswaService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class MuridHaidSiswaRepositoryImpl(
    private val muridHaidSiswaService: MuridHaidSiswaService,
    private val preferenceHelper: PreferenceHelper
): MuridHaidSiswaRepository {

    override fun tambahInfoHaid(requestBody: RequestBody): Flow<Result<GeneralResponse>> = flow {
        val response = muridHaidSiswaService.tambahInfoHaid(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Tambah Info Haid",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getInfoHaid(requestBody: RequestBody): Flow<Result<HaidSiswaResponse>> = flow {
        val response = muridHaidSiswaService.getInfoHaid(requestBody)
        emit(Result.createSuccess(data = response, preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "Get Info Haid",
                body = requestBody,
                error = error
            )
        )
    }
}
