package nishan.softient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nishan.softient.data.datasource.remote.GooglePlaceApi
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.*
import nishan.softient.domain.repository.GooglePlaceRepo

class GooglePlaceRepoImpl(private val googlePlaceApi: GooglePlaceApi) : GooglePlaceRepo {

    override suspend fun restaurants(map: Map<String, String>): FlowEventResult<GooglePlaceResult> = googlePlaceApi.restaurants(map).wrap().toFlow()
}