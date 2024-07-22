package com.sirawuh.data.domain

data class ListSiswaResponse(
    var message: String,
    var success: String,
    var data: List<DataListSiswaResponse>
)

data class DataListSiswaResponse(
    var idsiswa: String?,
    var nis: String?,
    var namasiswa: String?
)
