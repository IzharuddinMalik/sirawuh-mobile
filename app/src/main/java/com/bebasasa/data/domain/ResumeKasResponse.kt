package com.bebasasa.data.domain

data class ResumeKasResponse(
    var message: String,
    var success: String,
    var data: DataResumeKasResponse
)

data class DataResumeKasResponse(
    var namasiswa: String?,
    var jumlahbayarkas: String?
)