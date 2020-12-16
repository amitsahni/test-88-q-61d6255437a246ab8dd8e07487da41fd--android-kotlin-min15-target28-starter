package nishan.softient.data.datasource.remote

import nishan.softient.domain.entity.response.GooglePlaceResult
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface GooglePlaceApi {

    @GET("/maps/api/place/nearbysearch/json?location=47.6204,-122.3491&radius=2500&type=restaurant&key=AIzaSyClCtHSPnbIKfxv4S3C6LoGSjhFQmgcaIw")
    suspend fun restaurants(@QueryMap map: Map<String, String>): Response<GooglePlaceResult>

    companion object {
        fun create(retrofit: Retrofit) = retrofit.create(GooglePlaceApi::class.java)
    }
}