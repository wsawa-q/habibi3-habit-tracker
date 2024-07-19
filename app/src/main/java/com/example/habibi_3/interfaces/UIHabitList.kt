package com.example.habibi_3.interfaces

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.habibi_3.HabitViewModel
import com.example.habibi_3.db.Habit

@Composable
fun HabitList(habits: List<Habit>, habitViewModel: HabitViewModel, navController: NavHostController) {
    LazyColumn {
        items(habits) { habit ->
            HabitRow(habit = habit, habitViewModel = habitViewModel, navController = navController)
        }
    }
}