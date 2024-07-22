package com.sirawuh.data.domain

data class GuruSiswaHaidResponse(
    var message: String,
    var success: String,
    var data: List<ItemGuruSiswaHaidResponse>
)

data class ItemGuruSiswaHaidResponse(
    var namasiswa: String?,
    var tanggalhaid: String?
)
