package org.codejudge.application.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nishan.softient.domain.entity.wrapped.Event
import nishan.softient.domain.entity.wrapped.EventResult
import nishan.softient.domain.usecase.UseCase


/**
 * Created by Amit Singh on 14/12/20.
 * Tila
 * asingh@tila.com
 */

interface BaseTestUseCase<P, R> : UseCase<P, R> {

    override fun execute(scope: CoroutineScope, parameter: P?): Flow<EventResult<R>> {
        return flow {
            emit(Event(onExecute(parameter)))
        }
    }
}

enum class UiState {
    SUCCESS,
    ERROR
}