package org.codejudge.application.vm

import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase
import org.codejudge.application.extension.EventResultLiveData
import org.codejudge.application.extension.MutableEventResultLiveData

class GooglePlaceVM(
        private val placeUseCase: GooglePlaceBaseUseCase
) : BaseVM() {

    private val _googlePlaceLiveData = MutableEventResultLiveData<List<GooglePlaceResult>>()
    val googlePlaceLiveData: EventResultLiveData<List<GooglePlaceResult>> get() = _googlePlaceLiveData
    fun getPlace(request: GooglePlaceRequest) {
        placeUseCase.execute(request).collectFlow {
            _googlePlaceLiveData.postValue(it)
        }
    }
}