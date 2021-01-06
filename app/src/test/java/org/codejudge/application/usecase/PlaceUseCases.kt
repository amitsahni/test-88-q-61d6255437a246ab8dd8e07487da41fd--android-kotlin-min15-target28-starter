package org.codejudge.application.usecase

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.Event
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.entity.wrapped.Resource
import nishan.softient.domain.entity.wrapped.toFlow
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase
import nishan.softient.domain.usecase.getHTTPError
import timber.log.Timber

class GooglePlaceFakeUseCase(private val uiState: UiState) : GooglePlaceBaseUseCase {

    override suspend fun onExecute(parameter: GooglePlaceRequest?): FlowEventResult<GooglePlaceResult> {
        return when (uiState) {
            UiState.SUCCESS -> Resource.Success(200, GooglePlaceResult(emptyList()))
            UiState.ERROR -> Resource.Error(-1, Failure("Something went wrong"))
        }.toFlow()
    }

    override fun execute(parameter: GooglePlaceRequest?, isTesting: Boolean): FlowEventResult<GooglePlaceResult> {
        return super.execute(parameter, true)
    }
}