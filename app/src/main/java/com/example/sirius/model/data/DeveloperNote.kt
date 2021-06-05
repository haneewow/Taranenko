package com.example.sirius.model.data

import com.google.gson.annotations.SerializedName

data class DeveloperNote(
    @SerializedName("id")
    val id: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("votes")
    val votes: Int,
    @SerializedName("author")
    val authorName: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("gifURL")
    val gifUrl: String,
    @SerializedName("previewURL")
    val previewUrl: String,
)
