package com.example.sirius.domain.repository

import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result

interface DeveloperNoteRepository {
    suspend fun loadNote(number: Int): Result<DeveloperNote?>
}