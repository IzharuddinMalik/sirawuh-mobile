package com.sirawuh.data.domain

data class KasKelasSiswaResponse(
    var message: String,
    var success: String,
    var data: List<DataKasKelasSiswaResponse>
)

data class DataKasKelasSiswaResponse(
    var namasiswa: String?,
    var tanggalbayarkas: String?
)
