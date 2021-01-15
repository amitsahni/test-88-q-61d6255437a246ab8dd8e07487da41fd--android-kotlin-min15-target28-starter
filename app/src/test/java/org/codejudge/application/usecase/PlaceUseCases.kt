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
            UiState.SUCCESS -> {
               // val result = this.javaClass.classLoader.getResource("json/character_details.json").readText()
                //println(result)
                Resource.Success(200, emptyList<GooglePlaceResult>())
            }
            UiState.ERROR -> Resource.Error(-1, Failure("Something went wrong"))
        }.toFlow()
    }
}