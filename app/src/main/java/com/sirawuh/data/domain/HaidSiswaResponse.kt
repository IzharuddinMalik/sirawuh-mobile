package com.sirawuh.data.domain

data class HaidSiswaResponse(
    var message: String,
    var success: String,
    var data: List<DataHaidSiswaResponse>
)

data class DataHaidSiswaResponse(
    var namasiswa: String?,
    var tanggalhaid: String?
)
