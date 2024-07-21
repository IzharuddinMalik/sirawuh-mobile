package com.bebasasa.data.domain

data class GuruKehadiranSiswaResponse(
    var message: String,
    var success: String,
    var data: List<ItemGuruKehadiranSiswaResponse>
)

data class ItemGuruKehadiranSiswaResponse(
    var namasiswa: String?,
    var statuskehadiran: String?
)
