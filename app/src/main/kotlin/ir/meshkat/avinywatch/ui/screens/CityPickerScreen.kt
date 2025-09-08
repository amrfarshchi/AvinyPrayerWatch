package ir.meshkat.avinywatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import ir.meshkat.avinywatch.AvinyViewModel

@Composable
fun CityPickerScreen(vm: AvinyViewModel, onBack: () -> Unit) {
    val busy by vm.busy.collectAsState()
    val cities by vm.cities.collectAsState()
    val err by vm.error.collectAsState()
    var q by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { if (cities.isEmpty()) vm.searchCities() }

    Column(Modifier.fillMaxSize().padding(6.dp)) {
        Text("انتخاب شهر")
        Spacer(Modifier.height(6.dp))
        Text("در نوار جستجو تایپ کنید (فعلاً ساده)")
        Spacer(Modifier.height(6.dp))

        when {
            busy && cities.isEmpty() -> Text("در حال بارگذاری…")
            err != null -> Text("خطا: $err")
            else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(cities.filter { it.nameFa.contains(q) || it.nameEn.contains(q, true) }) { c ->
                    Chip(onClick = { vm.pickCity(c); onBack() }, label = { Text("${c.nameFa} (${c.code})") })
                }
            }
        }
    }
}
