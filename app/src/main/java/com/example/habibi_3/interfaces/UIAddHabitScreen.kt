package com.example.habibi_3.interfaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habibi_3.HabitViewModel
import com.example.habibi_3.db.Habit
import com.example.habibi_3.db.LogType

@Composable
fun AddHabitScreen(onHabitAdded: () -> Unit) {
    val habitViewModel: HabitViewModel = viewModel()

    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Box() {
                Text(
                    text = "New Habit",
                    fontSize = 35.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Box() {
                Text(
                    text = "Enter habit name:",
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            var habitName by remember { mutableStateOf("") }
            TextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text("Habit Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            var isDaily by remember { mutableStateOf(true) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDaily,
                    onCheckedChange = {
                        isDaily = it
                    }
                )
                Text("Daily", modifier = Modifier.weight(1f))
                Checkbox(
                    checked = !isDaily,
                    onCheckedChange = {
                        isDaily = !it
                    }
                )
                Text("Weekly", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if(habitName.isNotBlank()) {
                        val habit = Habit(
                            name = habitName.trim(),
                            type = if (isDaily) LogType.DAILY else LogType.WEEKLY,
                            isDone = false
                        )
                        habitViewModel.addHabit(habit)
                        onHabitAdded()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(60.dp)
            ) {
                Text("Add Habit", fontSize = 18.sp)
            }
        }
    }
}