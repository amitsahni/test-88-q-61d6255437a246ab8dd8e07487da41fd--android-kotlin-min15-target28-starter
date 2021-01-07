package org.codejudge.application.usecase

import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.entity.wrapped.Resource
import nishan.softient.domain.entity.wrapped.toFlow
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase

class GooglePlaceFakeUseCase(private val uiState: UiState) : GooglePlaceBaseUseCase {

    override suspend fun onExecute(parameter: GooglePlaceRequest?): FlowEventResult<List<GooglePlaceResult>> {
        return when (uiState) {
            UiState.SUCCESS -> Resource.Success(200, emptyList<GooglePlaceResult>())
            UiState.ERROR -> Resource.Error(-1, Failure("Something went wrong"))
        }.toFlow()
    }

    override fun execute(
        parameter: GooglePlaceRequest?,
        isTesting: Boolean
    ): FlowEventResult<List<GooglePlaceResult>> {
        return super.execute(parameter, true)
    }
}