package com.example.habibi_3.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

enum class LogType {
    DAILY,
    WEEKLY
}

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: LogType,
    var isDone: Boolean,
    val date: LocalDate? = null
)