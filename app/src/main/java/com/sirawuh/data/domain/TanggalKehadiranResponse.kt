package com.sirawuh.data.domain

data class TanggalKehadiranResponse(
    var message: String,
    var success: String,
    var data: List<ItemTanggalKehadiranResponse>
)

data class ItemTanggalKehadiranResponse(
    var idkehadiran: String?,
    var tanggalkehadiran: String?
)
