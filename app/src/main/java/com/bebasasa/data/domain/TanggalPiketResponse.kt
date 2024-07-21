package com.bebasasa.data.domain

data class TanggalPiketResponse(
    var message: String,
    var success: String,
    var data: List<ItemTanggalPiketResponse>
)

data class ItemTanggalPiketResponse(
    var tanggalpiket: String?
)
