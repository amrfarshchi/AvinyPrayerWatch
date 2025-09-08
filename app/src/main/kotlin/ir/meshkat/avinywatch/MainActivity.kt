package ir.meshkat.avinywatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Scaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: AvinyViewModel = viewModel(factory = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            val nav = rememberNavController()
            vm.observeSavedCity()
            MaterialTheme {
                Scaffold(timeText = { TimeText() }) {
                    AppNav(nav, vm)
                }
            }
        }
    }
}
