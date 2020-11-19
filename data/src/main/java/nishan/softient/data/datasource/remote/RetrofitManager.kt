package nishan.softient.data.datasource.remote

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import nishan.softient.data.BuildConfig
import nishan.softient.domain.extension.G
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import timber.log.Timber
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class RetrofitManager(
    private val context: Application
) {

    private val LINE_SEPARATOR = System.getProperty("line.separator")!!

    private val isDebug = BuildConfig.DEBUG

    private val okHttpInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!isDebug) return
                when {
                    message.startsWith("{") -> {
                        JSONObject(message).toString(1).let {
                            val lines =
                                it.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray()
                            for (line in lines) {
                                Timber.d("║ $line")
                            }
                        }
                    }
                    message.startsWith("[") -> {
                        JSONArray(message).toString(1)?.let {
                            val lines =
                                it.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray()
                            lines.forEach {
                                Timber.d("║ $it")
                            }
                        }
                    }
                    else -> {
                        Timber.d("║ $message")
                    }
                }
            }
        })
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val contentType = "application/json".toMediaType()
    private val okHttpClient =
        (if (BuildConfig.DEBUG) getUnsafeOkHttpClient() else OkHttpClient().newBuilder())
            .connectTimeout(10.toLong(), TimeUnit.SECONDS)
            .readTimeout(10.toLong(), TimeUnit.SECONDS)
            .writeTimeout(10.toLong(), TimeUnit.SECONDS)
            .addInterceptor(okHttpInterceptor)
            .build()

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(
            G.json.asConverterFactory(contentType)
        )
        .build()

    fun OkHttpClient.Builder.headers(headers: Map<String, String> = emptyMap()): OkHttpClient.Builder {
        if (headers.isNotEmpty()) {
            addInterceptor {
                val builder = it.request().newBuilder()
                headers.forEach { (key, value) ->
                    builder.addHeader(key, value)
                }
                it.proceed(builder.build())
            }
        }
        return this
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}