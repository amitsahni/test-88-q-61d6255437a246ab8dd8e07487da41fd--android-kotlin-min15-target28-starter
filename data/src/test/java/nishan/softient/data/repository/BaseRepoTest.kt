package nishan.softient.data.repository

import com.google.common.io.Resources
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import nishan.softient.domain.extension.G
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.logging.Logger


/**
 * Created by Amit Singh.
 * Tila
 * asingh@tila.com
 */
enum class UiState {
    SUCCESS,
    ERROR
}

open class BaseRepoTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var loggingInterceptor: HttpLoggingInterceptor
    private lateinit var retrofit: Retrofit

    fun retrofit() = retrofit

    fun mockServer() = mockWebServer

    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkhttpClient(loggingInterceptor)

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(G.json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    private fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    fun mockSuccessResponse(path : String) : MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .setBody(getJson(path))
    }

    fun mockErrorResponse(path : String) : MockResponse {
        return MockResponse()
            .setResponseCode(400)
            .setBody(getJson(path))
    }

    @Suppress("UnstableApiUsage")
    internal fun getJson(path: String): String {
        val uri = Resources.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())

    }

}