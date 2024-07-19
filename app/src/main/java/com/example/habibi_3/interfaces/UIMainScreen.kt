package com.example.habibi_3.interfaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habibi_3.HabitViewModel
import com.example.habibi_3.db.LogType

@Composable
fun MainScreen(navController: NavHostController) {
    val habitViewModel: HabitViewModel = viewModel()
    val habitsList = habitViewModel.getAllHabits.observeAsState(listOf()).value

    val dailyHabits = habitsList.filter { it.type == LogType.DAILY }
    val weeklyHabits = habitsList.filter { it.type == LogType.WEEKLY }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box() {
            MainText()
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box() {
            Text(
                text = "Daily Habits",
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())
        {
            HabitList(habits = dailyHabits, habitViewModel, navController)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Weekly Habits",
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

        {
            HabitList(habits = weeklyHabits, habitViewModel, navController)
        }
        Box(modifier = Modifier) {
            NavigationButtons(navController)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MainText() {
    Text(
        text = "Habibi 3",
        fontSize = 35.sp,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
    )
}
