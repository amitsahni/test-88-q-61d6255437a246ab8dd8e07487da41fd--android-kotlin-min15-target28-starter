/**
 * Created by Amit Singh on 17/12/20.
 * Tila
 * asingh@tila.com
 */

package nishan.softient.domain.entity.wrapped

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.extension.G

typealias EventResult<R> = Event<Resource<R>>
typealias FlowEventResult<R> = Flow<Event<Resource<R>>>

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

sealed class Resource<out T> {

    object Loading : Resource<Nothing>()
    data class Success<T>(val statusCode: Int, val data: T) : Resource<T>()
    data class Error<T>(val statusCode: Int, val error: Failure) : Resource<T>()

}

fun <T> retrofit2.Response<T>.wrap(): Resource<T> {
    return if (isSuccessful) {
        if (body() != null) {
            Resource.Success(code(), body()!!)
        } else {
            Resource.Error(code(), Failure("Something went wrong", code()))
        }
    } else {
        val failure = G.json.decodeFromString(Failure.serializer(), errorBody()?.string()!!)
        Resource.Error(code(), failure)
    }
}

fun <T> Resource<T>.unwrap(): T? {
    return if (this is Resource.Success<T>) {
        this.data
    } else {
        null
    }
}

fun <T> Event<Resource<T>>.toFlow() = flow {
    emit(this@toFlow)
}

fun <T> Resource<T>.toFlow() = flow {
    emit(Event(this@toFlow))
}