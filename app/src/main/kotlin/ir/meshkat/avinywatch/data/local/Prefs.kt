package ir.meshkat.avinywatch.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "aviny_prefs")

object Keys {
    val CITY_CODE = intPreferencesKey("city_code")
    val CITY_NAME_FA = stringPreferencesKey("city_name_fa")
    val CITY_NAME_EN = stringPreferencesKey("city_name_en")
    val USE_24H = booleanPreferencesKey("use_24h")
}

class Prefs(private val context: Context) {
    val cityFlow = context.dataStore.data.map { p: Preferences ->
        Pair(p[Keys.CITY_CODE], p[Keys.CITY_NAME_FA])
    }
    val use24hFlow = context.dataStore.data.map { it[Keys.USE_24H] ?: true }

    suspend fun saveCity(code: Int, nameFa: String, nameEn: String) {
        context.dataStore.edit {
            it[Keys.CITY_CODE] = code
            it[Keys.CITY_NAME_FA] = nameFa
            it[Keys.CITY_NAME_EN] = nameEn
        }
    }
    suspend fun setUse24h(v: Boolean) {
        context.dataStore.edit { it[Keys.USE_24H] = v }
    }
}
