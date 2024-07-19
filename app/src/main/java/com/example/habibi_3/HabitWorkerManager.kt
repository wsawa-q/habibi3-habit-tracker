package com.example.habibi_3

import android.content.Context
import androidx.work.*
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.concurrent.TimeUnit

class HabitWorkerManager(private val context: Context) {
    fun setupDailyResetWorker() {
        val midnight = LocalDate.now().atStartOfDay().minusMinutes(1)
        val currentDate = LocalDateTime.now()
        val initialDelay = Duration.between(currentDate, midnight)

        val dailyWorkRequest = PeriodicWorkRequestBuilder<ResetDailyHabitsWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay.seconds, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "resetDailyHabits",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }

    fun setupWeeklyResetWorker() {
        val today = LocalDate.now()
        val nextSunday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atStartOfDay().minusMinutes(1)
        val currentDate = LocalDateTime.now()
        val initialDelayWeek = Duration.between(currentDate, nextSunday)

        val weeklyWorkRequest = PeriodicWorkRequestBuilder<ResetWeeklyHabitsWorker>(7, TimeUnit.DAYS)
            .setInitialDelay(initialDelayWeek.seconds, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "resetWeeklyHabits",
            ExistingPeriodicWorkPolicy.KEEP,
            weeklyWorkRequest
        )
    }
}
