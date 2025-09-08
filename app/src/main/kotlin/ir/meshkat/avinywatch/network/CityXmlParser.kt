package ir.meshkat.avinywatch.network

import android.util.Xml
import ir.meshkat.avinywatch.data.model.City
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser

class CityXmlParser(
    private val client: OkHttpClient
) {
    private val endpoint = "https://prayer.aviny.com/api/city"

    suspend fun fetchAll(): List<City> = kotlinx.coroutines.suspendCancellableCoroutine { cont ->
        val req = Request.Builder().url(endpoint).build()
        val call = client.newCall(req)
        call.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                if (!cont.isCancelled) cont.resumeWith(Result.failure(e))
            }
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use { resp ->
                    if (!resp.isSuccessful) {
                        cont.resumeWith(Result.failure(IllegalStateException("HTTP ${resp.code}")))
                        return
                    }
                    try {
                        val parser: XmlPullParser = Xml.newPullParser()
                        parser.setInput(resp.body!!.charStream())
                        var event = parser.eventType
                        var tag: String? = null
                        var code: Int? = null
                        var nameFa: String? = null
                        var nameEn: String? = null
                        var province: Int? = null
                        var country: Int? = null
                        val list = mutableListOf<City>()
                        while (event != XmlPullParser.END_DOCUMENT) {
                            when (event) {
                                XmlPullParser.START_TAG -> { tag = parser.name }
                                XmlPullParser.TEXT -> {
                                    when (tag) {
                                        "Code" -> code = parser.text.toIntOrNull()
                                        "Name" -> nameFa = parser.text
                                        "LName" -> nameEn = parser.text
                                        "Province_Code" -> province = parser.text.toIntOrNull()
                                        "Country_Code" -> country = parser.text.toIntOrNull()
                                    }
                                }
                                XmlPullParser.END_TAG -> {
                                    if (parser.name == "City" && code != null && nameFa != null && nameEn != null) {
                                        list += City(code!!, nameFa!!, nameEn!!, province, country)
                                        code = null; nameFa = null; nameEn = null; province = null; country = null
                                    }
                                    tag = null
                                }
                            }
                            event = parser.next()
                        }
                        cont.resumeWith(Result.success(list))
                    } catch (t: Throwable) {
                        cont.resumeWith(Result.failure(t))
                    }
                }
            }
        })
        cont.invokeOnCancellation { call.cancel() }
    }
}
