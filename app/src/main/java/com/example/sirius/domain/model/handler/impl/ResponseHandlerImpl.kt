package com.example.sirius.domain.model.handler.impl

import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import com.example.sirius.domain.model.handler.ResponseHandler
import retrofit2.Response
import javax.inject.Inject

class ResponseHandlerImpl @Inject constructor() : ResponseHandler {
    override fun handleResponse(
        response: Response<DeveloperNote>,
        number: Int
    ): Result<DeveloperNote> =
        if (response.isSuccessful) {
            response.body()?.let {
                it.numberPage = number
                Result.Success(it)
            } ?: Result.Failure()
        } else Result.Failure(response.message())
}