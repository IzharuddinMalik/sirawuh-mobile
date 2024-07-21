package com.bebasasa.data.domain

data class LoginResponse(
    var message: String,
    var success: Int,
    var data: DataLoginResp
)

data class DataLoginResp(
    var nipnis: String,
)
