package ir.meshkat.avinywatch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.meshkat.avinywatch.data.local.Prefs
import ir.meshkat.avinywatch.data.model.City
import ir.meshkat.avinywatch.data.model.PrayerTimes
import ir.meshkat.avinywatch.data.repo.CityRepository
import ir.meshkat.avinywatch.data.repo.PrayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AvinyViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = Prefs(app)
    private val prayerRepo = PrayerRepository()
    private val cityRepo = CityRepository()

    private val _busy = MutableStateFlow(false)
    val busy: StateFlow<Boolean> = _busy.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _times = MutableStateFlow<PrayerTimes?>(null)
    val times: StateFlow<PrayerTimes?> = _times.asStateFlow()

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities.asStateFlow()

    val use24h = prefs.use24hFlow

    fun observeSavedCity() {
        viewModelScope.launch {
            prefs.cityFlow.collect { (code, nameFa) ->
                if (code != null && nameFa != null) {
                    _selectedCity.value = City(code, nameFa, nameFa, null, null)
                    refresh()
                }
            }
        }
    }

    fun refresh() {
        val code = _selectedCity.value?.code ?: return
        viewModelScope.launch {
            _busy.value = true; _error.value = null
            try {
                _times.value = prayerRepo.loadTimes(code)
            } catch (t: Throwable) {
                _error.value = t.message
            } finally { _busy.value = false }
        }
    }

    fun searchCities() {
        viewModelScope.launch {
            _busy.value = true; _error.value = null
            try { _cities.value = cityRepo.loadCities() }
            catch (t: Throwable) { _error.value = t.message }
            finally { _busy.value = false }
        }
    }

    fun pickCity(c: City) {
        viewModelScope.launch {
            prefs.saveCity(c.code, c.nameFa, c.nameEn)
            _selectedCity.value = c
            refresh()
        }
    }

    fun set24h(v: Boolean) { viewModelScope.launch { prefs.setUse24h(v) } }
}
