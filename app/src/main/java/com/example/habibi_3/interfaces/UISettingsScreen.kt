package com.example.habibi_3.interfaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habibi_3.HabitViewModel
import com.example.habibi_3.db.LogType

@Composable
fun SettingsScreen(onDone: () -> Unit) {
    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Box() {
                Text(
                    text = "Settings",
                    fontSize = 50.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            val habitViewModel: HabitViewModel = viewModel()

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { habitViewModel.setAllHabitsDone(false, LogType.DAILY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(60.dp)
            ) {
                Text("Reset Daily Habits")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { habitViewModel.setAllHabitsDone(false, LogType.WEEKLY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(60.dp)
            ) {
                Text("Reset Weekly Habits")
            }

        }
    }
}