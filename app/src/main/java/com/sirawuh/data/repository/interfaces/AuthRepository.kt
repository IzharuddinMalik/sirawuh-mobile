package com.sirawuh.data.repository.interfaces

import com.sirawuh.data.domain.LoginResponse
import com.sirawuh.data.domain.ProfileResponse
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface AuthRepository {

    fun login(requestBody: RequestBody): Flow<Result<LoginResponse>>
    fun getProfile(requestBody: RequestBody): Flow<Result<ProfileResponse>>
}
