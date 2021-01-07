package nishan.softient.data.datasource.remote

import nishan.softient.domain.entity.response.GooglePlaceResult
import nishan.softient.domain.entity.wrapped.Resource
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface GooglePlaceApi {

    @GET("/photos")
    suspend fun restaurants(@QueryMap map: Map<String, String>): Response<List<GooglePlaceResult>>

    companion object {
        fun create(retrofit: Retrofit) = retrofit.create(GooglePlaceApi::class.java)
    }
}