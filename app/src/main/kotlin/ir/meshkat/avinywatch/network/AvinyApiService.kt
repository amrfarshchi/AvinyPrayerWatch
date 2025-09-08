package ir.meshkat.avinywatch.network

import ir.meshkat.avinywatch.data.model.PrayerTimes
import retrofit2.http.GET
import retrofit2.http.Path

interface AvinyApiService {
    @GET("api/prayertimes/{code}")
    suspend fun getPrayerTimes(@Path("code") code: Int): PrayerTimes
}
