package ir.meshkat.avinywatch.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://prayer.aviny.com/"

    val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val moshi: Moshi by lazy { Moshi.Builder().build() }

    val api: AvinyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AvinyApiService::class.java)
    }

    val cityParser: CityXmlParser by lazy { CityXmlParser(okHttp) }
}
