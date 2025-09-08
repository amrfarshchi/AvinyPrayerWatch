package ir.meshkat.avinywatch.data.model

data class City(
    val code: Int,
    val nameFa: String,
    val nameEn: String,
    val provinceCode: Int?,
    val countryCode: Int?
)
