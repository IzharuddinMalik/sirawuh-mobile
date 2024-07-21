package com.bebasasa.data.domain

data class ResumeKehadiranResponse(
    var message: String,
    var success: String,
    var data: DataResumeKehadiranResponse
)

data class DataResumeKehadiranResponse(
    var hadir: List<ItemSiswaResumeKehadiranResponse>,
    var sakit: List<ItemSiswaResumeKehadiranResponse>,
    var izin: List<ItemSiswaResumeKehadiranResponse>
)

data class ItemSiswaResumeKehadiranResponse(
    var namasiswa: String?,
    var jumlahkehadiran: String?
)

