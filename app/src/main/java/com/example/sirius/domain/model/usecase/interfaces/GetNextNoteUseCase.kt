package com.example.sirius.domain.model.usecase.interfaces

import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import kotlinx.coroutines.flow.Flow

interface GetNextNoteUseCase {
    suspend operator fun invoke(number: Int): Flow<Result<DeveloperNote?>>
}