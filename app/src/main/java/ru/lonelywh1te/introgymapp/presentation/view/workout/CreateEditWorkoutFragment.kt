package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
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

        adapter.exerciseList = exerciseList
        return binding.root
    }

    private fun setWorkoutData(workout: Workout) {
        binding.etWorkoutName.setText(workout.name)
        binding.etWorkoutDescription.setText(workout.description)
    }

    private fun updateWorkout() {
        val updatedWorkout = workout!!.copy(name = binding.etWorkoutName.text.toString(), description = binding.etWorkoutDescription.text.toString())
        val exercises = exerciseList.map { it.exercise }

        lifecycleScope.launch {
            workoutViewModel.updateWorkout(updatedWorkout)
            exerciseViewModel.updateExercises(updatedWorkout.id, exercises)
            findNavController().popBackStack()
        }
    }

    private fun createWorkout() {
        val workout = Workout(binding.etWorkoutName.text.toString(), binding.etWorkoutDescription.text.toString())
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