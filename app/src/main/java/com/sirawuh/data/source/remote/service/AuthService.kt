package com.sirawuh.data.source.remote.service

import com.sirawuh.data.domain.LoginResponse
import com.sirawuh.data.domain.ProfileResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse

    @POST("auth/getprofileuser")
    suspend fun getProfile(@Body requestBody: RequestBody): ProfileResponse

}
