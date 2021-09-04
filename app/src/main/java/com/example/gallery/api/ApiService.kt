package com.example.gallery.api

import com.example.gallery.domain.model.data.DeveloperNotes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/{category}/{page}?")
    suspend fun getNotes(
        @Path("category") category: String,
        @Path("page") page: Int = 0,
        @Query("json") json: Boolean = true)
    : Response<DeveloperNotes>
}