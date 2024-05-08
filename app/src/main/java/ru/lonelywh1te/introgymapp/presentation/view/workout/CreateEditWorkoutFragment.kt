package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
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
import ru.lonelywh1te.introgymapp.databinding.FragmentCreateWorkoutBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseItemClick
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

class CreateEditWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentCreateWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var recycler: RecyclerView
    private val args: CreateEditWorkoutFragmentArgs by navArgs()

    private var editMode = false
    private var workout: Workout? = null
    private var exerciseList = mutableListOf<ExerciseWithInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewModel = getViewModel()
        exerciseViewModel = getViewModel()

        args.workout?.let {
            editMode = true
            workout = it
            exerciseViewModel.getAllExercisesWithInfoByWorkoutId(it.id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateWorkoutBinding.inflate(layoutInflater, container, false)
        workout?.let { setWorkoutData(it) }

        val adapter = ExerciseAdapter(object: OnExerciseItemClick {
            override fun onClick(item: ExerciseWithInfo, itemIndex: Int) {
                val action = CreateEditWorkoutFragmentDirections.toPlanExerciseFragment(item, itemIndex)
                findNavController().navigate(action)
            }
        })

        recycler = binding.rvWorkoutExercises
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnAddExercise.setOnClickListener {
            addExercise()
        }

        binding.btnSaveWorkout.setOnClickListener {
            if (!editMode) {
                createWorkout()
            } else {
                updateWorkout()
            }
        }

        setFragmentResultListener("ADD_EXERCISE") {_, bundle ->
            bundle.getParcelable<ExerciseWithInfo>("exercise")?.let {
                exerciseList.add(it)
                adapter.exerciseList = exerciseList
            }
        }

        setFragmentResultListener("CONFIG_EXERCISE") {_, bundle ->
            val index = bundle.getInt("exerciseIndex")
            bundle.getParcelable<ExerciseWithInfo>("exercise")?.let {changedExercise ->
                exerciseList[index] = changedExercise
                adapter.exerciseList = exerciseList
            }
        }

        if (exerciseList.isEmpty() && workout != null) {
            exerciseViewModel.exerciseWithInfoList.observe(viewLifecycleOwner) { list ->
                exerciseList = list.toMutableList()
                adapter.exerciseList = exerciseList
            }
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // TODO: не работает
                exerciseList.removeAt(viewHolder.absoluteAdapterPosition)
                Log.println(Log.DEBUG, "CreateEdit", "$exerciseList")
                adapter.exerciseList = exerciseList
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
        itemTouchHelper.attachToRecyclerView(recycler)

        adapter.exerciseList = exerciseList
        return binding.root
    }

    private fun setWorkoutData(workout: Workout) {
        binding.etWorkoutName.setText(workout.name)
        binding.etWorkoutDescription.setText(workout.description)
    }

    private fun updateWorkout() {
        if (binding.etWorkoutName.text.toString().isEmpty()) {
            binding.etWorkoutName.error = "Введите название"
            return
        }

        val updatedWorkout = workout!!.copy(name = binding.etWorkoutName.text.toString(), description = binding.etWorkoutDescription.text.toString(), exerciseCount = exerciseList.size)
        val exercises = exerciseList.map { it.exercise }

        lifecycleScope.launch {
            workoutViewModel.updateWorkout(updatedWorkout)
            exerciseViewModel.updateExercises(updatedWorkout.id, exercises)
            findNavController().popBackStack()
        }
    }

    private fun createWorkout() {
        if (binding.etWorkoutName.text.toString().isEmpty()) {
            binding.etWorkoutName.error = "Введите название"
            return
        }

        val workout = Workout(binding.etWorkoutName.text.toString(), binding.etWorkoutDescription.text.toString(), exerciseCount = exerciseList.size)
        val exercises = exerciseList.map { it.exercise }

        lifecycleScope.launch {
            workoutViewModel.createWorkout(workout)
            val workoutId = workoutViewModel.getLastCreatedWorkout().id
            exerciseViewModel.addExercises(workoutId, exercises)
            findNavController().popBackStack()
        }
    }

    private fun addExercise() {
        val action = CreateEditWorkoutFragmentDirections.toExerciseGuideFragment(true)
        findNavController().navigate(action)
    }
}