package com.example.habibi_3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.habibi_3.db.Habit
import com.example.habibi_3.db.HabitDatabase
import com.example.habibi_3.db.LogType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HabitRepository
    val getAllHabits: LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)

        getAllHabits = repository.getAllHabits
    }
    fun addHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }
    fun updateHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }
    fun deleteAllHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabits()
        }
    }

    fun getHabit(id: Int): Flow<Habit?> {
        return repository.getHabit(id)
    }

    fun setAllHabitsDone(isDone: Boolean, type: LogType) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAllHabitsIsDone(isDone, type)
        }
    }

    fun getHabits(type: LogType) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHabits(type)
        }
    }
    fun getLogsByDate(date: LocalDate, type: LogType): LiveData<List<Habit>> {
        return repository.getLogsByDate(date, type)
    }
    fun getLogsByNull(type: LogType): LiveData<List<Habit>> {
        return repository.getLogsByNull(type)
    }
}
