package nishan.softient.domain.repository

import kotlinx.coroutines.flow.Flow
import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.Event
import nishan.softient.domain.entity.wrapped.FlowEventResult
import nishan.softient.domain.entity.wrapped.Resource

interface GooglePlaceRepo {
    suspend fun restaurants(map: Map<String, String>): FlowEventResult<GooglePlaceResult>
}