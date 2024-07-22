package com.sirawuh.data.domain

data class ProfileResponse(
    var message: String,
    var success: String,
    var data: DataProfileResponse
)

data class DataProfileResponse (
    var iduser: String?,
    var nipnis: String?,
    var namauser: String?,
    var kelas: String?,
    var jeniskelamin: String?
)
