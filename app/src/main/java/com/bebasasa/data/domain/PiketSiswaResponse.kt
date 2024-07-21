package com.bebasasa.data.domain

data class PiketSiswaResponse(
    var message: String,
    var success: String,
    var data: List<DataPiketSiswaResponse>
)

data class DataPiketSiswaResponse(
    var namasiswa: String?,
    var fotopiket: String?,
    var tanggalpiket: String?
)
