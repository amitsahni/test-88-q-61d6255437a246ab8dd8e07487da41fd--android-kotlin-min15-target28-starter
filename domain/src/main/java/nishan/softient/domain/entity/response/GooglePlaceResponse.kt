package nishan.softient.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class GooglePlaceResult(
    val results: List<Result> = emptyList()
)

@Serializable
data class Result(
    val name: String? = null,
    val icon: String? = null,
    val rating: String? = null
)

