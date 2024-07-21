package com.bebasasa.data.repository.interfaces

import com.bebasasa.data.domain.LoginResponse
import com.bebasasa.data.domain.ProfileResponse
import com.bebasasa.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface AuthRepository {

    fun login(requestBody: RequestBody): Flow<Result<LoginResponse>>
    fun getProfile(requestBody: RequestBody): Flow<Result<ProfileResponse>>
}
