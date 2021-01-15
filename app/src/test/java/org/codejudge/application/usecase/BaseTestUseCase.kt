package org.codejudge.application.usecase

import com.google.common.io.Resources
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.Event
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.entity.wrapped.Resource
import nishan.softient.domain.usecase.getHTTPError
import timber.log.Timber
import java.io.File


enum class UiState {
    SUCCESS,
    ERROR
}

interface FlowUseCase<P, R> {

    suspend fun onExecute(parameter: P?): FlowEventResult<R>

    fun execute(
        parameter: P? = null,
        isTesting: Boolean = false
    ): FlowEventResult<R> {
        return flow {
            emitAll(onExecute(parameter))
        }.catch { e ->
            Timber.e(e)
            emit(Event(Resource.Error(-1, Failure(e.getHTTPError()))))
        }
    }
}

