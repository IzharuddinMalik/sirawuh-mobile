package com.bebasasa.data.domain

data class GuruPiketSiswaResponse(
    var message: String,
    var success: String,
    var data: List<ItemGuruPiketSiswaResponse>
)

data class ItemGuruPiketSiswaResponse(
    var namasiswa: String?,
    var fotopiket: String?
)