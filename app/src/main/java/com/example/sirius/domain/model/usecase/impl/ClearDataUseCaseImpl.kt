package com.example.sirius.domain.model.usecase.impl

import com.example.sirius.domain.model.usecase.interfaces.ClearDataUseCase
import com.example.sirius.domain.repository.DeveloperNoteRepository
import javax.inject.Inject

class ClearDataUseCaseImpl @Inject constructor(
    private val repository: DeveloperNoteRepository
): ClearDataUseCase {
    override suspend fun invoke() = repository.clearData()
}