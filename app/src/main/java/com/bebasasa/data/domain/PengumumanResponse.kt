package com.bebasasa.data.domain

data class PengumumanResponse(
    var message: String,
    var success: String,
    var data: List<DataPengumumanResponse>
)

data class DataPengumumanResponse(
    var idpengumumankelas: String?,
    var judulpengumuman: String?,
    var isipengumuman: String?,
    var filepengumuman: String?,
    var mediaextensi: String?,
    var created_date: String?
)
