package com.example.gallery.domain.model.usecase.interfaces

import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.Result
import kotlinx.coroutines.flow.Flow

interface GetLatestNoteUseCase {
    suspend operator fun invoke(number: Int): Flow<Result<DeveloperNote?>>
}