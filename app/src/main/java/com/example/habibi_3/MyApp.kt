package com.example.habibi_3

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habibi_3.interfaces.AddHabitScreen
import com.example.habibi_3.interfaces.EditHabitScreen
import com.example.habibi_3.interfaces.MainScreen
import com.example.habibi_3.interfaces.SettingsScreen

@Preview(showBackground = true)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable(
            route = "editHabit/{habitId}",
            arguments = listOf(navArgument("habitId") { type = NavType.IntType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId") ?: -1
            EditHabitScreen(habitId = habitId, onDone = { navController.navigate("main") })
        }

        composable("addHabit") { AddHabitScreen { navController.navigate("main") } }
        composable("calendar") { CalendarScreen { navController.navigate("main") } }
        composable("settings") { SettingsScreen { navController.navigate("main") } }
    }
}
