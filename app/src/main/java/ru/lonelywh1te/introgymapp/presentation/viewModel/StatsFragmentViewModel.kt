package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseHistoryByPeriodUseCase
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class StatsFragmentViewModel(
    private val getAllExerciseHistoryByPeriodUseCase: GetAllExerciseHistoryByPeriodUseCase
): ViewModel() {
    val exerciseHistory = MutableLiveData<List<ExerciseHistory>>()
    private val currentDate = LocalDate.now()

    fun getExerciseHistoryByPeriod(firstDate: Long, lastDate: Long) {
        viewModelScope.launch {
            exerciseHistory.postValue(getAllExerciseHistoryByPeriodUseCase.execute(firstDate, lastDate))
        }
    }

    fun getEntriesByCurrentMonth(): List<Entry> {
        val entries = mutableListOf<Entry>()
        val history = exerciseHistory.value

        if (history.isNullOrEmpty()) return emptyList()

        for(day in 1..currentDate.lengthOfMonth()){
            val date = currentDate.withDayOfMonth(day).toEpochDay() * 86400000L
            val historyAtDate = history.filter { it.date == date }
            val weight = historyAtDate.sumOf { it.reps * it.weight }

            if (weight > 0f) entries.add(Entry(day.toFloat() - 1, weight.toFloat()))
        }

        return entries
    }

    fun getXAxisCurrentMonthLabels(): List<String> {
        val labels = mutableListOf<String>()

        for(i in 1..currentDate.lengthOfMonth()){
            val monthName = currentDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
            labels.add("$i $monthName")
        }

        return labels
    }
}