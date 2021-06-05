package com.example.sirius.domain.model.usecase.impl

import com.example.sirius.domain.model.data.Result
import com.example.sirius.domain.model.usecase.interfaces.GetNoteUseCase
import com.example.sirius.domain.repository.DeveloperNoteRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetNoteUseCaseImpl @Inject constructor(
    private val repository: DeveloperNoteRepository
) : GetNoteUseCase {

    override suspend fun invoke(number: Int) = flow {
        val result = repository.loadNote(number)
        emit(result)
    }.onStart {
        emit(Result.Loading)
    }
}