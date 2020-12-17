package nishan.softient.data.repository

import nishan.softient.data.datasource.remote.GooglePlaceApi
import nishan.softient.domain.entity.wrapped.wrap
import nishan.softient.domain.repository.GooglePlaceRepo

class GooglePlaceRepoImpl(private val googlePlaceApi: GooglePlaceApi) : GooglePlaceRepo {
    override suspend fun restaurants(map: Map<String, String>) =
            googlePlaceApi.restaurants(map).wrap()

}