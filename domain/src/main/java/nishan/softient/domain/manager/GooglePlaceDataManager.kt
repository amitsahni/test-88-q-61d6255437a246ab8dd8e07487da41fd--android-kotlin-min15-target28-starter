package nishan.softient.domain.manager

import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.repository.GooglePlaceRepo

open class GooglePlaceDataManager(private val googlePlaceRepo: GooglePlaceRepo) : GooglePlaceRepo {
    override suspend fun restaurants(map: Map<String, String>): FlowEventResult<List<GooglePlaceResult>> =
        googlePlaceRepo.restaurants(map)

}