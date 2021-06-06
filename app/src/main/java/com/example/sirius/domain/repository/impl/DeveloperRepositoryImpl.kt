package com.example.sirius.domain.repository.impl

import android.util.Log
import com.example.sirius.api.ApiService
import com.example.sirius.core.db.AppDao
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import com.example.sirius.domain.model.handler.ResponseHandler
import com.example.sirius.domain.repository.DeveloperNoteRepository
import retrofit2.HttpException
import javax.inject.Inject

class DeveloperRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val handler: ResponseHandler,
    private val dao: AppDao
) : DeveloperNoteRepository {
    override suspend fun loadNote(number: Int): Result<DeveloperNote?> {
        return try {
            val noteFromDB = dao.getNoteForNumber(number)
            if (noteFromDB == null) getNotesFromApi(number)
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

    private suspend fun getNotesFromApi(number: Int): Result<DeveloperNote?> {
        val result = handler.handleResponse(api.getNotes(), number)
        saveResult(result)
        return result
    }

    private suspend fun saveResult(
        result: Result<DeveloperNote?>
    ) {
        if (result is Result.Success) {
            result.data?.let {
                dao.saveNote(it)
            }
        }
    }
}