package com.example.sirius.domain.model.handler.impl

import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Notes
import com.example.sirius.domain.model.data.Result
import com.example.sirius.domain.model.handler.ResponseHandler
import retrofit2.Response
import javax.inject.Inject

class ResponseHandlerImpl @Inject constructor() : ResponseHandler {
    override fun handleResponse(response: Response<Notes>, number: Int): Result<DeveloperNote?> =
        if (response.isSuccessful) {
            response.body()?.let {
                val result = it.developerNotes.firstOrNull()
                result?.numberPage = number
                Result.Success(result)
            } ?: Result.Failure()
        } else Result.Failure(response.message())
}