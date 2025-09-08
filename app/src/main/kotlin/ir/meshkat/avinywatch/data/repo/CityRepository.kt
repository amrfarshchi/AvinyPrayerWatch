package ir.meshkat.avinywatch.data.repo

import ir.meshkat.avinywatch.data.model.City
import ir.meshkat.avinywatch.network.NetworkModule

class CityRepository {
    suspend fun loadCities(): List<City> = NetworkModule.cityParser.fetchAll()
}
