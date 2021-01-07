package nishan.softient.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class GooglePlaceResult(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String? = null,
    val thumbnailUrl: String? = null
)

