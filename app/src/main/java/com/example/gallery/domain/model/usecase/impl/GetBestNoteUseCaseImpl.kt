package com.example.gallery.domain.model.usecase.impl

import com.example.gallery.domain.model.data.Result
import com.example.gallery.domain.model.usecase.interfaces.GetBestNoteUseCase
import com.example.gallery.domain.repository.DeveloperNoteRepository
import com.example.gallery.ui.adapter.Category
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetBestNoteUseCaseImpl @Inject constructor(
    private val repository: DeveloperNoteRepository
) : GetBestNoteUseCase {

    override suspend fun invoke(number: Int) = flow {
        val result = repository.loadNote(number, Category.TOP)
        emit(result)
    }.onStart {
        emit(Result.Loading)
    }
}