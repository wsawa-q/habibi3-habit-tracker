package com.example.habibi_3

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.habibi_3.db.HabitDatabase
import com.example.habibi_3.db.Habit
import com.example.habibi_3.db.LogType
import java.time.LocalDate

class ResetWeeklyHabitsWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val database = HabitDatabase.getDatabase(applicationContext)
        val habitDao = database.habitDao()
        val habitLogDao = database.habitDao()

        val currentDate = LocalDate.now()

        return try {
            val allHabits = habitDao.getHabits(LogType.WEEKLY)

            allHabits.forEach { habit ->
                val habitLog = Habit(
                    name = habit.name,
                    type = habit.type,
                    isDone = habit.isDone,
                    date = currentDate
                )
                habitLogDao.addHabit(habitLog)
            }

            habitDao.updateAllHabitsIsDone(false, LogType.WEEKLY)

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}

class ResetDailyHabitsWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val database = HabitDatabase.getDatabase(applicationContext)
        val habitDao = database.habitDao()
        val habitLogDao = database.habitDao()

        val currentDate = LocalDate.now()

        return try {
            val allHabits = habitDao.getHabits(LogType.DAILY)

            allHabits.forEach { habit ->
                val habitLog = Habit(
                    name = habit.name,
                    type = habit.type,
                    isDone = habit.isDone,
                    date = currentDate
                )
                habitLogDao.addHabit(habitLog)
            }

            habitDao.updateAllHabitsIsDone(false, LogType.DAILY)

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}