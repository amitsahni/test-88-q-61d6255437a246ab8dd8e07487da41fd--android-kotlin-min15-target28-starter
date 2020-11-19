package nishan.softient.domain.manager

import nishan.softient.domain.repository.GooglePlaceRepo

class GooglePlaceDataManager(private val googlePlaceRepo: GooglePlaceRepo) : GooglePlaceRepo {
    override suspend fun restaurants(map: Map<String, String>) = googlePlaceRepo.restaurants(map)
}