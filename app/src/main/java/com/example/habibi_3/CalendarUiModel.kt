package com.example.habibi_3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habibi_3.db.Habit
import com.example.habibi_3.db.LogType
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.stream.Collectors
import java.util.stream.Stream

data class CalendarUiModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
) {
    
    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))
    }
}

class CalendarDataSource {
    val today: LocalDate
        get() {
        return LocalDate.now()
    }

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}

@Composable
fun CalendarHeader(
    data: CalendarUiModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,) {
    Row {
        IconButton(onClick = {
            onPrevClickListener(data.startDate.date)
        }) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Back"
            )
        }
        Text(
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                )
            },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onNextClickListener(data.endDate.date)
        }) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun CalendarContentItem(date: CalendarUiModel.Date, onClickListener: (CalendarUiModel.Date) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable {
                onClickListener(date)
            }
        ,
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun CalendarContent(data: CalendarUiModel, onDateClickListener: (CalendarUiModel.Date) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyRow {
            items(items = data.visibleDates) { date ->
                CalendarContentItem(date, onDateClickListener)
            }
        }
    }
}

@Composable
fun CalendarApp(modifier: Modifier = Modifier) {
    val dataSource = CalendarDataSource()
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
    val habitViewModel: HabitViewModel = viewModel()

    val selectedDateState = remember { mutableStateOf(calendarUiModel.selectedDate.date) }
    val today = LocalDate.now()
    val isTodaySelected = today.isEqual(selectedDateState.value)

    val nextSunday = selectedDateState.value.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val isCurrentWeekSelected = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).isEqual(nextSunday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)))

    val onDateClickListener: (CalendarUiModel.Date) -> Unit = { date ->
        selectedDateState.value = date.date
        calendarUiModel = calendarUiModel.copy(
            selectedDate = date,
            visibleDates = calendarUiModel.visibleDates.map {
                it.copy(isSelected = it.date.isEqual(date.date))
            }
        )
    }

    val habitsDailyList = if (isTodaySelected) {
        habitViewModel.getLogsByNull(LogType.DAILY).observeAsState(listOf()).value
    } else {
        habitViewModel.getLogsByDate(selectedDateState.value, LogType.DAILY).observeAsState(listOf()).value
    }

    val habitsWeeklyList = if (isCurrentWeekSelected) {
        habitViewModel.getLogsByNull(LogType.WEEKLY).observeAsState(listOf()).value
    } else {
        habitViewModel.getLogsByDate(nextSunday, LogType.WEEKLY).observeAsState(listOf()).value
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CalendarHeader(
            data = calendarUiModel,
            onPrevClickListener = { startDate ->
                val finalStartDate = startDate.minusDays(1)
                calendarUiModel = dataSource.getData(startDate = finalStartDate, lastSelectedDate = calendarUiModel.selectedDate.date)
            },
            onNextClickListener = { endDate ->
                val finalStartDate = endDate.plusDays(2)
                calendarUiModel = dataSource.getData(startDate = finalStartDate, lastSelectedDate = calendarUiModel.selectedDate.date)
            }
        )
        CalendarContent(data = calendarUiModel, onDateClickListener = onDateClickListener)

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Daily Logs", modifier = Modifier.align(Alignment.Center))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Gray)),
        ) {
            HabitsListDisplay(habitsList = habitsDailyList)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Weekly Logs", modifier = Modifier.align(Alignment.Center))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Gray)),
        ) {
            HabitsListDisplay(habitsList = habitsWeeklyList)
        }
    }
}

@Composable
fun CalendarScreen(onDone: () -> Unit) {

    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Calendar",
                    fontSize = 50.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            ) {
                CalendarApp()
            }

        }
    }
}

@Composable
fun HabitsListDisplay(habitsList: List<Habit>) {
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        habitsList.forEach { habitLog ->
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = habitLog.name,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (habitLog.isDone) "Done" else "Not Done",
                    color = if (habitLog.isDone) Color.Green else Color.Red
                )
            }
        }
    }
}