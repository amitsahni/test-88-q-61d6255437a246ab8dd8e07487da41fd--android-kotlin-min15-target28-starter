package org.codejudge.application.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.usecase.GooglePlaceUseCase

class GooglePlaceVM(
    private val placeUseCase: GooglePlaceUseCase
) : BaseVM() {

    override fun onAction(event: UserEvent) {
        when (event) {
            is PlaceSearchEvent.Place -> {
                _googlePlaceLiveData.value = event.request
            }
        }
    }

    private val _googlePlaceLiveData = MutableLiveData<GooglePlaceRequest>()
    val googlePlaceLiveData = Transformations.switchMap(_googlePlaceLiveData) {
        placeUseCase.execute(viewModelIOScope, it).toLiveData()
    }
}