package com.example.habibi_3.interfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NavigationButtons(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { navController.navigate("calendar") }) {
            Text("Calendar")
        }
        Button(onClick = { navController.navigate("addHabit") }) {
            Text("Add Habit")
        }
        Button(onClick = { navController.navigate("settings") }) {
            Text("Settings")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
