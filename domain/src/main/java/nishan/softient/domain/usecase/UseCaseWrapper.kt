package nishan.softient.domain.usecase

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.Event
import nishan.softient.domain.entity.wrapped.EventResult
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.entity.wrapped.Resource
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import java.util.concurrent.TimeoutException

interface UseCase<P, R> {
    suspend fun onExecute(parameter: P?): EventResult<R>

    fun execute(
        parameter: P? = null,
        isTesting: Boolean = false
    ): FlowEventResult<R> {
        return flow {
            emit(Event(Resource.Loading))
            emit(onExecute(parameter))
        }.catch { e ->
            Timber.e(e)
            emit(Event(Resource.Error(-1, Failure(e.getHTTPError()))))
        }
    }

    suspend fun executes(parameter: P? = null): EventResult<R> {
        return try {
            onExecute(parameter)
        } catch (e: Exception) {
            Event(Resource.Error(-1, Failure(e.getHTTPError())))
        }
    }
}

interface FlowUseCase<P, R> {

    suspend fun onExecute(parameter: P?): FlowEventResult<R>

    fun execute(
        parameter: P? = null,
        isTesting: Boolean = false
    ): FlowEventResult<R> {
        return flow {
            if (!isTesting) {
                emit(Event(Resource.Loading))
            }
            emitAll(onExecute(parameter))
        }.catch { e ->
            Timber.e(e)
            emit(Event(Resource.Error(-1, Failure(e.getHTTPError()))))
        }
    }
}

fun Throwable.getHTTPError(): String {
    return if (javaClass.name.contains(UnknownHostException::class.java.name)) {
        "Check your internet connection and try again"
    } else if (javaClass.name.contains(TimeoutException::class.java.name)
        || javaClass.name.contains(SocketTimeoutException::class.java.name)
        || javaClass.name.contains(ConnectException::class.java.name)
    ) {
        "Something went wrong"
    } else if (javaClass.name.contains(CertificateException::class.java.name)) {
        "Something went wrong"
    } else {
        toString()
    }
}