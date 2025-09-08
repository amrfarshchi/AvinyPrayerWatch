package ir.meshkat.avinywatch.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import ir.meshkat.avinywatch.AvinyViewModel

@Composable
fun SettingsScreen(vm: AvinyViewModel, onBack: () -> Unit) {
    val use24 = vm.use24h.collectAsState(initial = true).value
    Column(Modifier.fillMaxSize().padding(8.dp)) {
        Text("تنظیمات")
        ToggleChip(checked = use24, onCheckedChange = { vm.setUse24h(it) }, label = { Text("نمایش ۲۴ ساعته") })
        Button(onClick = onBack) { Text("بازگشت") }
    }
}
