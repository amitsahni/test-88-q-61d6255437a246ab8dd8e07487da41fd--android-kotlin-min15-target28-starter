package org.codejudge.application.usecase

import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.entity.wrapped.Resource
import nishan.softient.domain.entity.wrapped.toFlow
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase


/**
 * Created by Amit Singh.
 * Tila
 * asingh@tila.com
 */

class GooglePlaceUseCaseTest : GooglePlaceBaseUseCase {

    override suspend fun onExecute(parameter: GooglePlaceRequest?): FlowEventResult<List<GooglePlaceResult>> {
        return Resource.Success(200, emptyList<GooglePlaceResult>()).toFlow()
    }
}