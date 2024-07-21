package com.bebasasa.data.source.remote.response

data class ApiResponse<out T>(
    val data: T?,
    val error: String?,
    val jwt: String?,
) {
    companion object {
        fun <T> createResponse(data: T?, jwt: String? = null): ApiResponse<T> {
            return ApiResponse(
                data = data,
                error = null,
                jwt = jwt
            )
        }
    }
}
