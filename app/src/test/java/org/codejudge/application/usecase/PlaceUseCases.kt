package org.codejudge.application.usecase

import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.Result
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase

class GooglePlaceFakeUseCase(var uiState: UiState) : GooglePlaceBaseUseCase {

    override suspend fun onExecute(parameter: GooglePlaceRequest?): Result<GooglePlaceResult> {
        return when (uiState) {
            UiState.SUCCESS -> {
                Result.Success(GooglePlaceResult(emptyList()))
            }

            UiState.ERROR -> {
                Result.Error(Failure("Something went wrong"))
            }
        }
    }
}