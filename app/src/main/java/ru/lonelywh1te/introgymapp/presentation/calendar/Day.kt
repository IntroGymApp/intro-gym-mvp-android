package ru.lonelywh1te.introgymapp.presentation.calendar

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class Day (
    val localDate: LocalDate,

    val dayOfMouth: Int = localDate.dayOfMonth,
    val dayOfWeek: String = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase(),
    val month: String = localDate.month.toString(),
    val year: Int = localDate.year,
    val date: Long = localDate.toEpochDay() * 86400000L,
    val isSelected: Boolean = false,
)