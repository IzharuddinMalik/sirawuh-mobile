package com.bebasasa.data.domain

data class ResumePiketResponse(
    var message: String,
    var success: String,
    var data: List<ItemResumePiketResponse>
)

data class ItemResumePiketResponse(
    var namasiswa: String?,
    var jumlahpiket: String?
)
