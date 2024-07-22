package com.sirawuh.data.domain

data class KehadiranSiswaResponse(
    var message: String,
    var success: String,
    var data: List<DataKehadiranSiswaResponse>
)

data class DataKehadiranSiswaResponse(
    var namasiswa: String?,
    var statuskehadiran: String?,
    var tanggalkehadiran: String?,
    var fotokehadiran: String?
)
