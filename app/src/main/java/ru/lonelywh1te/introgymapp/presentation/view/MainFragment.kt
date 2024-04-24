package ru.lonelywh1te.introgymapp.presentation.view

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentMainBinding
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.calendar.CalendarAdapter
import ru.lonelywh1te.introgymapp.presentation.calendar.Day
import ru.lonelywh1te.introgymapp.presentation.calendar.OnItemClickListener
import ru.lonelywh1te.introgymapp.presentation.calendar.WeeklyCalendar
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnWorkoutItemClick
import ru.lonelywh1te.introgymapp.presentation.view.adapter.WorkoutAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.WorkoutDateAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel
import java.time.LocalDate

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var calendarRecycler: RecyclerView
    private lateinit var workoutRecycler: RecyclerView
    private val weeklyCalendar = WeeklyCalendar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewModel = getViewModel()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val calendarAdapter = CalendarAdapter(object : OnItemClickListener {
            override fun onDayItemClick(item: Day) {
                weeklyCalendar.selectedDate = item.localDate
                workoutViewModel.getAllWorkoutsByDate(item.date)
            }
        })

        val workoutAdapter = WorkoutDateAdapter(object: OnWorkoutItemClick {
            override fun onClick(item: Workout) {
                val action = MainFragmentDirections.toWorkoutViewFragment(item, true)
                findNavController().navigate(action)
            }
        })

        calendarRecycler = binding.rvCalendar
        calendarRecycler.apply {
            this.adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

        workoutRecycler = binding.rvTrainingAtDay
        workoutRecycler.apply {
            this.adapter = workoutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnPrevWeek.setOnClickListener {
            weeklyCalendar.setPreviousWeek()
        }

        binding.btnNextWeek.setOnClickListener {
            weeklyCalendar.setNextWeek()
        }

        binding.ibAddWorkoutToDate.setOnClickListener {
            val action = MainFragmentDirections.toWorkoutFragment(date = weeklyCalendar.selectedDate.toEpochDay() * 86400000L, pickMode = true)
            findNavController().navigate(action)
        }

        weeklyCalendar.week.observe(viewLifecycleOwner) {
            calendarAdapter.week = it

            val currentMonth = weeklyCalendar.getMonthNameOfWeek(it)
            val currentYear = weeklyCalendar.getYearOfWeek(it)
            binding.tvCalendarCurrentMonthYear.text = "${it.first().dayOfMouth}-${it.last().dayOfMouth} $currentMonth $currentYear"
        }

        workoutViewModel.workoutList.observe(viewLifecycleOwner) {
            workoutAdapter.workoutList = it
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        workoutViewModel.getAllWorkoutsByDate(weeklyCalendar.selectedDate.toEpochDay() * 86400000L)
    }
}