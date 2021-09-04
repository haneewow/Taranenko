package com.example.gallery.domain.model.data

import com.google.gson.annotations.SerializedName

data class DeveloperNotes(
    @SerializedName("result")
    val notes: List<DeveloperNote>?,
    val totalCount: Long?
)