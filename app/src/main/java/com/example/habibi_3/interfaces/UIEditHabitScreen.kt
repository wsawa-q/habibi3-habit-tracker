package com.example.habibi_3.interfaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habibi_3.HabitViewModel
import com.example.habibi_3.db.LogType

@Composable
fun EditHabitScreen(habitId: Int, onDone: () -> Unit, habitViewModel: HabitViewModel = viewModel()) {
    val habit by habitViewModel.getHabit(habitId).collectAsStateWithLifecycle(initialValue = null)

    val (habitName, setHabitName) = remember(habit) { mutableStateOf(habit?.name ?: "") }
    val (habitType, setHabitType) = remember(habit) { mutableStateOf(habit?.type ?: LogType.WEEKLY) }

    LaunchedEffect(habit) {
        setHabitName(habit?.name ?: "")
        setHabitType(habit?.type ?: LogType.WEEKLY)
    }

    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TextField(
                value = habitName,
                onValueChange = setHabitName,
                label = { Text("Habit Name") }
            )

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = habitType == LogType.DAILY,
                        onClick = { setHabitType(LogType.DAILY) }
                    )
                    Text("Daily")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = habitType == LogType.WEEKLY,
                        onClick = { setHabitType(LogType.WEEKLY) }
                    )
                    Text("Weekly")
                }
            }

            Button(onClick = {
                habit?.let {
                    val updatedHabit = it.copy(name = habitName, type = habitType)
                    habitViewModel.updateHabit(updatedHabit)
                }
                onDone()
            }) {
                Text("Save Changes")
            }

            Button(onClick = {
                habit?.let {
                    habitViewModel.deleteHabit(it)
                }
                onDone()
            }) {
                Text("Delete Habit")
            }
        }
    }
}

