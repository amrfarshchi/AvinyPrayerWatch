package ir.meshkat.avinywatch.data.model

import com.squareup.moshi.Json

data class PrayerTimes(
    @Json(name = "CityName") val cityName: String?,
    @Json(name = "CountryName") val countryName: String?,
    @Json(name = "CityLName") val cityLName: String?,
    @Json(name = "CountryLName") val countryLName: String?,
    @Json(name = "Today") val todayJalali: String?,
    @Json(name = "TodayQamari") val todayQamari: String?,
    @Json(name = "Imsaak") val imsaak: String?,
    @Json(name = "Sunrise") val sunrise: String?,
    @Json(name = "Noon") val noon: String?,
    @Json(name = "Sunset") val sunset: String?,
    @Json(name = "Maghreb") val maghreb: String?,
    @Json(name = "Midnight") val midnight: String?,
    @Json(name = "TimeZone") val timeZone: String?
)
