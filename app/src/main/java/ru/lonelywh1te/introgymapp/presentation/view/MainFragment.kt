package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.FragmentMainBinding
import ru.lonelywh1te.introgymapp.presentation.calendar.CalendarAdapter
import ru.lonelywh1te.introgymapp.presentation.calendar.Day
import ru.lonelywh1te.introgymapp.presentation.calendar.OnItemClickListener
import ru.lonelywh1te.introgymapp.presentation.calendar.WeeklyCalendar
import java.time.LocalDate

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var calendarRecycler: RecyclerView
    private val weeklyCalendar = WeeklyCalendar()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val adapter = CalendarAdapter(object : OnItemClickListener {
            override fun onDayItemClick(item: Day) {
                weeklyCalendar.selectedDate = item.localDate
            }
        })

        calendarRecycler = binding.rvCalendar
        calendarRecycler.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

        binding.btnPrevWeek.setOnClickListener {
            weeklyCalendar.setPreviousWeek()
        }

        binding.btnNextWeek.setOnClickListener {
            weeklyCalendar.setNextWeek()
        }

        weeklyCalendar.week.observe(viewLifecycleOwner) {
            adapter.week = it

            val currentMonth = weeklyCalendar.getMonthNameOfWeek(it)
            val currentYear = weeklyCalendar.getYearOfWeek(it)
            binding.tvCalendarCurrentMonthYear.text = "${it.first().dayOfMouth}-${it.last().dayOfMouth} $currentMonth $currentYear"
        }

        return binding.root
    }
}