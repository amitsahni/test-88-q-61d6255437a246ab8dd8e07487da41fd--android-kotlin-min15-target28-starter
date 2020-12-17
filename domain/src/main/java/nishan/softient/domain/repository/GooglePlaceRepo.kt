package nishan.softient.domain.repository

import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.Resource

interface GooglePlaceRepo {
    suspend fun restaurants(map: Map<String, String>): Resource<GooglePlaceResult>
}