/**
 * Created by Amit Singh on 17/12/20.
 * Tila
 * asingh@tila.com
 */

package nishan.softient.domain.entity.wrapped

import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.extension.G

typealias EventResult<R> = Event<Resource<R>>

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

/*data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}*/

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