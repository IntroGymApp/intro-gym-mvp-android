package ru.lonelywh1te.introgymapp.presentation.calendar

import androidx.lifecycle.MutableLiveData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class WeeklyCalendar {
    val week = MutableLiveData<List<Day>>()

    private var currentDate: LocalDate = LocalDate.now()
    var selectedDate: LocalDate = currentDate
        set(value) {
            field = value
            updateWeek()
        }

    private var firstDayOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    init {
        updateWeek()
    }

    fun getCurrentWeek() {
        firstDayOfWeek = null
        updateWeek()
    }

    private fun updateWeek() {
        val newWeek = mutableListOf<Day>()
        var dayOfWeek = firstDayOfWeek

        for (i in 0 until 7) {
            if (dayOfWeek == selectedDate) newWeek.add(Day(dayOfWeek, isSelected = true))
            else newWeek.add(Day(dayOfWeek))

            dayOfWeek = dayOfWeek.plusDays(1)
        }

        week.value = newWeek
    }

    fun setPreviousWeek() {
        firstDayOfWeek = firstDayOfWeek.minusWeeks(1)
        updateWeek()
    }

    fun setNextWeek() {
        firstDayOfWeek = firstDayOfWeek.plusWeeks(1)
        updateWeek()
    }

    fun getMonthNameOfWeek(week: List<Day>): String {
        val months = week.map { it.month }.distinct()

        if (months.size == 1) return months[0]

        val countFirst = week.filter { it.month == months[0] }.size
        val countSecond =  week.filter { it.month == months[1] }.size

        return if (countFirst > countSecond) months[0] else months[1]
    }

    fun getYearOfWeek(week: List<Day>): Int {
        val months = week.map { it.year }.distinct()
        if (months.size == 1) return months[0]

        val countFirst = week.filter { it.year == months[0] }.size
        val countSecond =  week.filter { it.year == months[1] }.size

        return if (countFirst > countSecond) months[0] else months[1]
    }
}