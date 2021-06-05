package com.example.sirius.domain.model.data

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Failure(val msg: String? = null, val code: Int? = null) : Result<Nothing>()
}