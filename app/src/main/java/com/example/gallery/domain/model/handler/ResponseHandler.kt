package com.example.gallery.domain.model.handler

import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.DeveloperNotes
import com.example.gallery.domain.model.data.Result
import com.example.gallery.ui.adapter.Category
import retrofit2.Response

interface ResponseHandler {
    fun handleResponse(
        response: Response<DeveloperNotes>,
        number: Int,
        category: Category
    ): Result<DeveloperNote>
}