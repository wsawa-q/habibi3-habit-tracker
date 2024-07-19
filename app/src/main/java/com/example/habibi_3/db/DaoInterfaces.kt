package com.example.habibi_3.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit_table WHERE date is NULL ORDER BY id DESC")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE type = :type and date is NULL ORDER BY id DESC")
    suspend fun getHabits(type: LogType): List<Habit>

    @Query("SELECT * FROM habit_table WHERE id = :id")
    fun getHabit(id: Int): Flow<Habit>

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll()

    @Query("UPDATE habit_table SET isDone = :isDone WHERE type = :type AND date is NULL")
    suspend fun updateAllHabitsIsDone(isDone: Boolean, type: LogType)
    @Query("SELECT * FROM habit_table WHERE date = :date AND type = :type ORDER BY id DESC")
    fun getLogsByDate(date: LocalDate, type: LogType): LiveData<List<Habit>>
    @Query("SELECT * FROM habit_table WHERE date is NULL AND type = :type ORDER BY id DESC")
    fun getLogsByNull(type: LogType): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE date is NULL AND type = :type ORDER BY id DESC")
    fun getLogsByNullStatic(type: LogType): List<Habit>
}
