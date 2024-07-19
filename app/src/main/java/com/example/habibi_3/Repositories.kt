package com.example.habibi_3

import androidx.lifecycle.LiveData
import com.example.habibi_3.db.Habit
import com.example.habibi_3.db.HabitDao
import com.example.habibi_3.db.LogType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class HabitRepository (private val habitDao: HabitDao) {
    val getAllHabits: LiveData<List<Habit>> = habitDao.getAllHabits()
    val habitDetails: StateFlow<Habit?> = MutableStateFlow(null)

    suspend fun addHabit(habit: Habit) {
        habitDao.addHabit(habit)
    }
    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }
    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }
    suspend fun deleteAllHabits() {
        habitDao.deleteAll()
    }

    fun getHabit(id: Int): Flow<Habit?> {
        return habitDao.getHabit(id)
    }

    suspend fun updateAllHabitsIsDone(isDone: Boolean, type: LogType) {
        habitDao.updateAllHabitsIsDone(isDone, type)
    }

    suspend fun getHabits(type: LogType) {
        habitDao.getHabits(type)
    }
    fun getLogsByDate(date: LocalDate, type: LogType): LiveData<List<Habit>> {
        return habitDao.getLogsByDate(date, type)
    }
    fun getLogsByNull(type: LogType): LiveData<List<Habit>> {
        return habitDao.getLogsByNull(type)
    }
}
