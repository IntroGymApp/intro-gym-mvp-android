package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnWorkoutItemClick
import ru.lonelywh1te.introgymapp.presentation.view.adapter.WorkoutAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var recycler: RecyclerView

    private val args: WorkoutFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewModel = getViewModel()
        exerciseViewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        val adapter = WorkoutAdapter(object: OnWorkoutItemClick {
            override fun onClick(item: Workout) {
                if (args.pickMode) {
                    addWorkoutDate(item)
                } else {
                    val action = WorkoutFragmentDirections.toWorkoutViewFragment(item.id, item.name.toString())
                    findNavController().navigate(action)
                }
            }
        })

        recycler = binding.rvWorkouts
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                workoutViewModel.deleteWorkout(adapter.workoutList[viewHolder.absoluteAdapterPosition])
                Toast.makeText(requireContext(), "Тренировка удалена", Toast.LENGTH_SHORT).show()

                workoutViewModel.getAllWorkouts()
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
        if (!args.pickMode) itemTouchHelper.attachToRecyclerView(recycler)

        binding.btnCreateWorkout.setOnClickListener {
            val action = WorkoutFragmentDirections.toCreateWorkout()
            findNavController().navigate(action)
        }

        workoutViewModel.workoutList.observe(viewLifecycleOwner) {
            adapter.workoutList = it
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        workoutViewModel.getAllWorkouts()
    }

    private fun addWorkoutDate(workout: Workout){
        lifecycleScope.launch {
            workoutViewModel.addWorkoutDate(workout, args.date)
            val workoutId = workoutViewModel.getLastCreatedWorkout().id
            val exercises = exerciseViewModel.getAllExercisesByWorkoutId(workout.id)
            exerciseViewModel.addExercises(workoutId, exercises)

            findNavController().popBackStack()
        }
    }
}