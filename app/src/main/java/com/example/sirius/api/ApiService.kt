package com.example.sirius.api

import com.example.sirius.domain.model.data.DeveloperNote
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("random?json=true")
    suspend fun getNotes(): Response<DeveloperNote>
}