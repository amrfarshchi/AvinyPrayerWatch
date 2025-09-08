package ir.meshkat.avinywatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import ir.meshkat.avinywatch.AvinyViewModel
import ir.meshkat.avinywatch.data.model.PrayerTimes

@Composable
fun HomeScreen(vm: AvinyViewModel, onOpenCities: () -> Unit, onOpenSettings: () -> Unit) {
    val busy by vm.busy.collectAsState()
    val error by vm.error.collectAsState()
    val times by vm.times.collectAsState()
    val city by vm.selectedCity.collectAsState()

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = city?.nameFa ?: "لطفاً شهر را انتخاب کنید", textAlign = TextAlign.Center)
        Spacer(Modifier.size(8.dp))

        when {
            busy -> { Text("در حال بارگذاری…") }
            error != null -> { Text("خطا: $error") }
            times != null -> TimesList(times!!)
            else -> Text("از منوی پایین شهر را انتخاب کنید")
        }

        Spacer(Modifier.height(8.dp))
        Row {
            Button(onClick = onOpenCities) { Text("انتخاب شهر") }
            Spacer(Modifier.width(6.dp))
            Button(onClick = { vm.refresh() }) { Text("به‌روزرسانی") }
            Spacer(Modifier.width(6.dp))
            Button(onClick = onOpenSettings) { Text("تنظیمات") }
        }
    }
}

@Composable
private fun TimesList(t: PrayerTimes) {
    CompactChip(onClick = {}, label = { Text("امساک: ${t.imsaak ?: "--:--"}") })
    CompactChip(onClick = {}, label = { Text("طلوع: ${t.sunrise ?: "--:--"}") })
    CompactChip(onClick = {}, label = { Text("ظهر: ${t.noon ?: "--:--"}") })
    CompactChip(onClick = {}, label = { Text("غروب: ${t.sunset ?: "--:--"}") })
    CompactChip(onClick = {}, label = { Text("مغرب: ${t.maghreb ?: "--:--"}") })
    CompactChip(onClick = {}, label = { Text("نیمه‌شب: ${t.midnight ?: "--:--"}") })
}
