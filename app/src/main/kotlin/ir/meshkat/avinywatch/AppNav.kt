package ir.meshkat.avinywatch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.meshkat.avinywatch.ui.screens.CityPickerScreen
import ir.meshkat.avinywatch.ui.screens.HomeScreen
import ir.meshkat.avinywatch.ui.screens.SettingsScreen

object Routes { const val Home = "home"; const val Cities = "cities"; const val Settings = "settings" }

@Composable
fun AppNav(nav: NavHostController, vm: AvinyViewModel) {
    NavHost(navController = nav, startDestination = Routes.Home) {
        composable(Routes.Home) { HomeScreen(vm, onOpenCities = { nav.navigate(Routes.Cities) }, onOpenSettings = { nav.navigate(Routes.Settings) }) }
        composable(Routes.Cities) { CityPickerScreen(vm, onBack = { nav.popBackStack() }) }
        composable(Routes.Settings) { SettingsScreen(vm, onBack = { nav.popBackStack() }) }
    }
}
