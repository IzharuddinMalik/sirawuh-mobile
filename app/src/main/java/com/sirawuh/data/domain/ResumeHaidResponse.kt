package com.sirawuh.data.domain

data class ResumeHaidResponse(
    var message: String,
    var success: String,
    var data: List<DataResumeHaidResponse>
)

data class DataResumeHaidResponse(
    var namasiswa: String?,
    var totalhaid: String?
)
