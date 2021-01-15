package nishan.softient.data.repository

import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import nishan.softient.data.datasource.remote.GooglePlaceApi
import nishan.softient.domain.entity.wrapped.error
import nishan.softient.domain.entity.wrapped.success
import nishan.softient.domain.repository.GooglePlaceRepo
import org.junit.Before
import org.junit.Test


class GooglePlaceRepoImplTest : BaseRepoTest() {

    private lateinit var googlePlaceRepoImpl: GooglePlaceRepo
    private lateinit var googlePlaceApi: GooglePlaceApi

    @Before
    override fun setup() {
        super.setup()
        googlePlaceApi = GooglePlaceApi.create(retrofit())
        googlePlaceRepoImpl = GooglePlaceRepoImpl(googlePlaceApi)
    }


    @Test
    fun getRest_Success() = runBlocking {
        mockServer().enqueue(mockSuccessResponse("json/character_details.json"))
        googlePlaceRepoImpl.restaurants(emptyMap()).collect {
            println("getRest_Success = ${it.peekContent().success()}")
            Truth.assertThat(it.peekContent().success()?.size).isEqualTo(5000)
        }
        googlePlaceRepoImpl.restaurants(emptyMap()).take(1).toList()
    }

    @Test
    fun getRest_Error() = runBlocking {
        mockServer().enqueue(mockErrorResponse("json/character_details_error.json"))
        googlePlaceRepoImpl.restaurants(emptyMap()).collect {
            println("getRest_Error = ${it.peekContent().error()}")
            val result = it.peekContent().error()
            Truth.assertThat(result?.statusCode).isEqualTo(400)
        }
    }


}