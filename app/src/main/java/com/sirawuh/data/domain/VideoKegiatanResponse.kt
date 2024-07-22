package com.sirawuh.data.domain

data class VideoKegiatanResponse(
    var message: String,
    var success: String,
    var data: List<DataVideoKegiatanResponse>
)

data class DataVideoKegiatanResponse(
    var idvideokegiatan: String?,
    var judulvideokegiatan: String?,
    var deskripsikegiatan: String?,
    var filevideokegiatan: String?,
    var mediaextensi: String?,
    var created_date: String?,
    var author: String?
)
