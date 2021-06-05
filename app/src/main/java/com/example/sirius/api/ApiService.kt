package com.example.sirius.api

import com.example.sirius.domain.model.data.Notes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("latest/{pageNumber}?json=true")
    suspend fun getNotes(@Path("pageNumber") page: Int): Response<Notes>
}