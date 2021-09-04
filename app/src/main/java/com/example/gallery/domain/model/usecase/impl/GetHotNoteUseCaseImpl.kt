package com.example.gallery.domain.model.usecase.impl

import com.example.gallery.domain.model.data.Result
import com.example.gallery.domain.model.usecase.interfaces.GetHotNoteUseCase
import com.example.gallery.domain.repository.DeveloperNoteRepository
import com.example.gallery.ui.adapter.Category
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetHotNoteUseCaseImpl @Inject constructor(
    private val repository: DeveloperNoteRepository
) : GetHotNoteUseCase {

    override suspend fun invoke(number: Int) = flow {
        val result = repository.loadNote(number, Category.HOT)
        emit(result)
    }.onStart {
        emit(Result.Loading)
    }
}