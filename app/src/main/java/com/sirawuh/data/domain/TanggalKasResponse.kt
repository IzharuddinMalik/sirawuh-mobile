package com.sirawuh.data.domain

data class TanggalKasResponse(
    var message: String,
    var success: String,
    var data: List<ItemTanggalKasResponse>
)

data class ItemTanggalKasResponse(
    var tanggalbayarkas: String?
)
