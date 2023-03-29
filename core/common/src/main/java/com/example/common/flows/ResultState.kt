package com.example.common.flows

import kotlinx.coroutines.runBlocking

sealed class ResultState<T> {

    /**
     * Convert the [ResultState] type to another type.
     */
    fun <R> map(mapper: ((T) -> R)? = null): ResultState<R> {
        return runBlocking {
            val suspendMapper: (suspend (T) -> R)? = if (mapper == null) {
                null
            } else {
                {
                    mapper(it)
                }
            }
            suspendMap(suspendMapper)
        }
    }

    fun getValueOrNull(): T? {
        if (this is Success<T>) return this.value
        return null
    }

    fun isFinished() = this is Success<T> || this is Error<T>

    /**
     * Convert the [ResultState] type to another type by using a suspend lambda.
     */
    abstract suspend fun <R> suspendMap(mapper: (suspend (T) -> R)? = null): ResultState<R>
}

class Success<T>(
    val value: T
) : ResultState<T>() {
    override suspend fun <R> suspendMap(mapper: (suspend (T) -> R)?): ResultState<R> {
        if (mapper == null) throw IllegalStateException("Can't map Container.Success without mapper")
        return try {
            Success(mapper(value))
        } catch (e: Exception) {
            Error(e)
        }
    }
}

class Error<T>(
    val error: Throwable
) : ResultState<T>() {
    override suspend fun <R> suspendMap(mapper: (suspend (T) -> R)?): ResultState<R> {
        return Error(error = error)
    }

}

class Empty<T> : ResultState<T>() {
    override suspend fun <R> suspendMap(mapper: (suspend (T) -> R)?): ResultState<R> {
        return Empty()
    }
}

class Pending<T> : ResultState<T>() {
    override suspend fun <R> suspendMap(mapper: (suspend (T) -> R)?): ResultState<R> {
        return Pending()
    }
}