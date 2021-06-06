package com.example.sirius.domain.model.handler

import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import retrofit2.Response

interface ResponseHandler {
    fun handleResponse(response: Response<DeveloperNote>, number: Int): Result<DeveloperNote>
}