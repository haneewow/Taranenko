package com.example.gallery.domain.model.handler.impl

import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.DeveloperNotes
import com.example.gallery.domain.model.data.Result
import com.example.gallery.domain.model.handler.ResponseHandler
import com.example.gallery.ui.adapter.Category
import retrofit2.Response
import javax.inject.Inject

class ResponseHandlerImpl @Inject constructor() : ResponseHandler {
    override fun handleResponse(
        response: Response<DeveloperNotes>,
        number: Int,
        category: Category
    ): Result<DeveloperNote> =
        if (response.isSuccessful) {
            response.body()?.let {
                val note = it.notes?.get(number)
                Result.Success(note)
            } ?: Result.Failure()
        } else Result.Failure(response.message())
}