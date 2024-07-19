package com.example.habibi_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.habibi_3.db.HabitDao
import com.example.habibi_3.db.HabitDatabase
import com.example.habibi_3.db.LogType
import com.example.habibi_3.ui.theme.Habibi3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var db: HabitDatabase
    private lateinit var habitDao: HabitDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = HabitDatabase.getDatabase(this)
        habitDao = db.habitDao()

        val habitWorkerManager = HabitWorkerManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            val weeklyHabits = habitDao.getLogsByNullStatic(LogType.WEEKLY)
            val dailyHabits = habitDao.getLogsByNullStatic(LogType.DAILY)
            withContext(Dispatchers.Main) {
                if (weeklyHabits.isNotEmpty()) {
                    habitWorkerManager.setupWeeklyResetWorker()
                }

                if (dailyHabits.isNotEmpty()) {
                    habitWorkerManager.setupDailyResetWorker()
                }
            }
        }

        setContent {
            Habibi3Theme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    MyApp()
                }
            }
        }
    }
}