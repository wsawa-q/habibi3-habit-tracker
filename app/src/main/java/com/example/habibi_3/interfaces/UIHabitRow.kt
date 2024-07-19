package com.example.habibi_3.interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.habibi_3.HabitViewModel
import com.example.habibi_3.db.Habit

@Composable
fun HabitRow(habit: Habit, habitViewModel: HabitViewModel, navController: NavHostController) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable {
            navController.navigate("editHabit/${habit.id}")
        }) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)) {
            Checkbox(
                checked = habit.isDone,
                onCheckedChange = {
                    val updatedHabit = habit.copy(isDone = !habit.isDone)
                    habitViewModel.updateHabit(updatedHabit)
                }
            )
            Text(text = habit.name, modifier = Modifier.padding(start = 8.dp))
        }
    }
}