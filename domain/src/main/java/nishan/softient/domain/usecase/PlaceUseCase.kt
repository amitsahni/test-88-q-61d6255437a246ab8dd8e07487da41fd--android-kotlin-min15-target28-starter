package nishan.softient.domain.usecase

import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.toResult
import nishan.softient.domain.extension.fromJson
import nishan.softient.domain.extension.toJson
import nishan.softient.domain.manager.GooglePlaceDataManager

typealias GooglePlaceBaseUseCase = UseCase<GooglePlaceRequest, GooglePlaceResult>

class GooglePlaceUseCase(private val dataManager: GooglePlaceDataManager) :
        GooglePlaceBaseUseCase {
    override suspend fun onExecute(parameter: GooglePlaceRequest?) =
            dataManager.restaurants(parameter?.toJson().fromJson()).toResult()

}