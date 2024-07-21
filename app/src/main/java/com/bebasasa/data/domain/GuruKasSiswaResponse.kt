package com.bebasasa.data.domain

data class GuruKasSiswaResponse(
    var message: String,
    var success: String,
    var data: List<ItemGuruKasSiswaResponse>
)

data class ItemGuruKasSiswaResponse(
    var namasiswa: String?
)
