package com.example.sirius.domain.model.handler

import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Notes
import com.example.sirius.domain.model.data.Result
import retrofit2.Response

interface ResponseHandler {
    fun handleResponse(response: Response<Notes>, number: Int): Result<DeveloperNote?>
}