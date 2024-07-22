package com.sirawuh.data.repository.implementation

import com.sirawuh.data.domain.LoginResponse
import com.sirawuh.data.domain.ProfileResponse
import com.sirawuh.data.repository.interfaces.AuthRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.AuthService
import com.sirawuh.data.source.remote.service.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val preferenceHelper: PreferenceHelper
) : AuthRepository {
    override fun login(requestBody: RequestBody): Flow<Result<LoginResponse>> = flow {
        val response = authService.login(requestBody)
        emit(Result.createSuccess(data = response, preference = preferenceHelper))
    }.catch { error ->
        emit(
            Result.createError(
                label = "login",
                body = requestBody,
                error = error
            )
        )
    }

    override fun getProfile(requestBody: RequestBody): Flow<Result<ProfileResponse>> = flow {
        val response = authService.getProfile(requestBody)
        emit(Result.createSuccess(data = response, preference = preferenceHelper))
    }.catch {error ->
        emit(
            Result.createError(
                label = "Get Profile",
                body = requestBody,
                error = error
            )
        )
    }
}