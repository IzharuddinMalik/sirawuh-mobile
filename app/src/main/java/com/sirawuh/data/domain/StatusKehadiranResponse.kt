package com.sirawuh.data.domain

data class StatusKehadiranResponse(
    var message: String,
    var success: String,
    var data: List<DataStatusKehadiranResponse>
)

data class DataStatusKehadiranResponse(
    var idstatuskehadiran: String?,
    var statuskehadiran: String?
)
