package com.example.gallery.domain.repository.impl

import android.util.Log
import com.example.gallery.api.ApiService
import com.example.gallery.core.db.AppDao
import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.Result
import com.example.gallery.domain.model.handler.ResponseHandler
import com.example.gallery.domain.repository.DeveloperNoteRepository
import com.example.gallery.ui.adapter.Category
import retrofit2.HttpException
import javax.inject.Inject

class DeveloperRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val handler: ResponseHandler,
    private val dao: AppDao
) : DeveloperNoteRepository {
    private var page = 0
    private var indexNotes = 0

    override suspend fun loadNote(number: Int, category: Category): Result<DeveloperNote?> {
        return try {
            val noteFromDB = dao.getNoteForNumber(number, category)
            if (noteFromDB == null) getNotesFromApi(number, category)
            else Result.Success(noteFromDB)
        } catch (e: HttpException) {
            Log.d(
                this::class.simpleName,
                "HttpException when loading notes: " + e.message + " with code " + e.code()
            )
            Result.Failure(e.message(), e.code())
        } catch (e: Exception) {
            Log.d(this::class.simpleName, "Exception when loading notes: " + e.message)
            Result.Failure(e.message)
        }
    }

    private suspend fun getNotesFromApi(number: Int, category: Category): Result<DeveloperNote?> {
        if (number % 5 == 0) page++
        if (number % 4 == 0) indexNotes = 0
        val response = api.getNotes(category.name.toLowerCase(), page)
        val result = handler.handleResponse(response, indexNotes, category)
            .also {
                it.mapOnSuccess { data -> data?.let {
                    it.category = category
                    it.numberPage = number
                    dao.saveNote(data) }
                }
            }

        indexNotes++
        return result
    }
}