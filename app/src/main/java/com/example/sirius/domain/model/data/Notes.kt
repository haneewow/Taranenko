package com.example.sirius.domain.model.data

import com.google.gson.annotations.SerializedName

data class Notes(
    @SerializedName("result")
    val developerNotes: List<DeveloperNote>,
    @SerializedName("totalCount")
    val totalCount: Long
)