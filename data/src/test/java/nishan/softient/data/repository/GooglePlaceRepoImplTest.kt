package nishan.softient.data.repository

import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import nishan.softient.data.datasource.remote.GooglePlaceApi
import nishan.softient.domain.entity.wrapped.error
import nishan.softient.domain.entity.wrapped.success
import nishan.softient.domain.repository.GooglePlaceRepo
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
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
    fun getRest_Success() {
        mockServer().dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/character_details.json"))
            }
        }
        runBlocking {
            googlePlaceRepoImpl.restaurants(emptyMap()).collect {
                println("getRest_Success = ${it.peekContent().success()}")
                Truth.assertThat(it.peekContent().success()?.size).isEqualTo(5000)
            }
        }
    }

    @Test
    fun getRest_Error() {
        mockServer().dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(400)
                    .setBody(getJson("json/character_details_error.json"))
            }
        }
        runBlocking {
            googlePlaceRepoImpl.restaurants(emptyMap()).collect {
                println("getRest_Error = ${it.peekContent().error()}")
                val result = it.peekContent().error()
                Truth.assertThat(result?.statusCode).isEqualTo(400)
            }
        }
    }


}