package org.codejudge.application.vm

import nishan.softient.domain.entity.request.GooglePlaceRequest

interface UserEvent

sealed class PlaceSearchEvent : UserEvent {
    data class Place(val request: GooglePlaceRequest) : PlaceSearchEvent()
}