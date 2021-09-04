package com.example.gallery.core.db

import androidx.room.TypeConverter
import com.example.gallery.ui.adapter.Category

class EnumConverter {

    @TypeConverter
    fun toCategory(value: String) = enumValueOf<Category>(value)

    @TypeConverter
    fun fromCategory(value: Category) = value.name
}