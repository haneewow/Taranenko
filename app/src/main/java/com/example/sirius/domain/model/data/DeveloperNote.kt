package com.example.sirius.domain.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class DeveloperNote(
    @PrimaryKey
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
    @SerializedName("gifSize")
    val gifSize: Long,
    @SerializedName("videoURL")
    val videoUrl: String,
    @SerializedName("videoPath")
    val videoPath: String,
    @SerializedName("videoSize")
    val videoSize: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("commentsCount")
    val commentsCount: Long,
    @SerializedName("fileSize")
    val fileSize: Long,
    @SerializedName("canVote")
    val isCanVote: Boolean,
    var numberPage: Int
): Serializable
