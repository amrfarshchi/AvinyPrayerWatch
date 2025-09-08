package ir.meshkat.avinywatch.data.repo

import ir.meshkat.avinywatch.data.model.PrayerTimes
import ir.meshkat.avinywatch.network.NetworkModule

class PrayerRepository {
    suspend fun loadTimes(cityCode: Int): PrayerTimes =
        NetworkModule.api.getPrayerTimes(cityCode)
}
