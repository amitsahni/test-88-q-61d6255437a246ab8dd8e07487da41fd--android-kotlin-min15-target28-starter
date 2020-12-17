package org.codejudge.application.usecase

import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.Resource
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase

class GooglePlaceFakeUseCase(var uiState: UiState) : GooglePlaceBaseUseCase {

    override suspend fun onExecute(parameter: GooglePlaceRequest?): Resource<GooglePlaceResult> {
        return when (uiState) {
            UiState.SUCCESS -> {
                Resource.Success(200, GooglePlaceResult(emptyList()))
            }

            UiState.ERROR -> {
                Resource.Error(-1, Failure("Something went wrong"))
            }
        }
    }
}