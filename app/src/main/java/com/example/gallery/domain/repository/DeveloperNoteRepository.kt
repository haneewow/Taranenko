package com.example.gallery.domain.repository

import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.Result
import com.example.gallery.ui.adapter.Category

interface DeveloperNoteRepository {
    suspend fun loadNote(number: Int, category: Category): Result<DeveloperNote?>
}