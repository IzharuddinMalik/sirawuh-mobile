package com.bebasasa.data.source.remote.service

import com.bebasasa.data.domain.LoginResponse
import com.bebasasa.data.domain.ProfileResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse

    @POST("auth/getprofileuser")
    suspend fun getProfile(@Body requestBody: RequestBody): ProfileResponse

}
