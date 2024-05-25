package ru.lonelywh1te.introgymapp.presentation.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentMainBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.calendar.CalendarAdapter
import ru.lonelywh1te.introgymapp.domain.calendar.Day
import ru.lonelywh1te.introgymapp.domain.calendar.OnItemClickListener
import ru.lonelywh1te.introgymapp.domain.calendar.WeeklyCalendar
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnWorkoutItemClick
import ru.lonelywh1te.introgymapp.presentation.view.adapter.WorkoutDateAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.MainFragmentViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var calendarRecycler: RecyclerView
    private lateinit var workoutRecycler: RecyclerView
    private val weeklyCalendar = WeeklyCalendar()

    private lateinit var userData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, MODE_PRIVATE)
        viewModel = getViewModel()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val userName = userData.getString(Constants.USER_NAME_KEY, "Пользователь")

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.tvHelloUser.text = "С возвращением, $userName!"

        val calendarAdapter = CalendarAdapter(object : OnItemClickListener {
            override fun onDayItemClick(item: Day) {
                weeklyCalendar.selectedDate = item.localDate
                viewModel.getWorkoutsByDate(item.date)
            }
        })

        val workoutAdapter = WorkoutDateAdapter(object: OnWorkoutItemClick {
            override fun onClick(item: Workout) {
                val action = MainFragmentDirections.toWorkoutViewFragment(item.id, item.name.toString(), true)
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

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteWorkout(workoutAdapter.workoutList[viewHolder.absoluteAdapterPosition])
                Toast.makeText(requireContext(), "Тренировка удалена", Toast.LENGTH_SHORT).show()

                viewModel.getWorkoutsByDate(weeklyCalendar.selectedDate.toEpochDay() * 86400000L)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftLabel("Удалить")
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.negative_color))
                    .setSwipeLeftLabelColor(MaterialColors.getColor(viewHolder.itemView, R.attr.ig_defaultTextColor))
                    .addSwipeLeftCornerRadius(1, 10f)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(workoutRecycler)

        binding.btnPrevWeek.setOnClickListener {
            weeklyCalendar.setPreviousWeek()
        }

        binding.btnNextWeek.setOnClickListener {
            weeklyCalendar.setNextWeek()
        }

        binding.ibAddWorkoutToDate.setOnClickListener {
            if (viewModel.allWorkoutsCount == 0) {
                Toast.makeText(requireContext(), "Вы еще не составили ни одной тренировки", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val action = MainFragmentDirections.toPickWorkoutFragment(date = weeklyCalendar.selectedDate.toEpochDay() * 86400000L, pickMode = true)
            findNavController().navigate(action)
        }

        weeklyCalendar.week.observe(viewLifecycleOwner) {
            calendarAdapter.week = it

            val currentMonth = weeklyCalendar.getMonthNameOfWeek(it)
            val currentYear = weeklyCalendar.getYearOfWeek(it)
            binding.tvCalendarCurrentMonthYear.text = "${it.first().dayOfMouth}-${it.last().dayOfMouth} $currentMonth $currentYear"
        }

        viewModel.workoutList.observe(viewLifecycleOwner) {
            binding.noWorkoutsLayout.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

            workoutAdapter.workoutList = it
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.countAllWorkouts()
        viewModel.getWorkoutsByDate(weeklyCalendar.selectedDate.toEpochDay() * 86400000L)
    }
}