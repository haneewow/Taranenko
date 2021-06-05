package com.example.sirius.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int
)