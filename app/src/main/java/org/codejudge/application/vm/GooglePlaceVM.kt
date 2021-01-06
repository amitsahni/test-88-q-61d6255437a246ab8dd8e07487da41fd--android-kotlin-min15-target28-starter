package org.codejudge.application.vm

import androidx.annotation.VisibleForTesting
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase
import org.codejudge.application.extension.EventResultLiveData
import org.codejudge.application.extension.MutableEventResultLiveData

class GooglePlaceVM(
        private val placeUseCase: GooglePlaceBaseUseCase
) : BaseVM() {
    var isTesting: Boolean = false

    private val _googlePlaceLiveData = MutableEventResultLiveData<GooglePlaceResult>()
    val googlePlaceLiveData: EventResultLiveData<GooglePlaceResult> get() = _googlePlaceLiveData
    fun getPlace(request: GooglePlaceRequest) {
        placeUseCase.execute(request, isTesting).collectFlow {
            _googlePlaceLiveData.postValue(it)
        }
    }
}