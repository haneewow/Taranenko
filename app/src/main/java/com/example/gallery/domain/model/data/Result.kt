package com.example.gallery.domain.model.data

sealed class Result <out T: Any?> {
    object Loading : Result<Nothing>()

    data class Success<out T : Any>(val data: T?) : Result<T>()

    data class Failure(
        val msg: String? = null,
        val code: Int? = null
    ) : Result<Nothing>()

    inline fun <Type> handleResult(
        successDelegate: (T?) -> Type,
        failureDelegate: (String?) -> Type? = { null },
        loadingDelegate: () -> Type? = { null }
    ): Type? = when (this) {
        is Success -> successDelegate(this.data)
        is Failure -> failureDelegate(this.msg)
        is Loading -> loadingDelegate()
    }

    inline fun<R> mapOnSuccess(block: (T?) -> R?): Result<R> {
        return when(this) {
            is Success -> {
                val resultValue = block.invoke(data)
                Success(resultValue)
            }
            is Failure -> Failure(msg)
            is Loading -> Loading
        }
    }
}