package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.databinding.FragmentCreateWorkoutBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseItemClick
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

class CreateWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentCreateWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var recycler: RecyclerView
    private val exerciseList = mutableListOf<ExerciseWithInfo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateWorkoutBinding.inflate(layoutInflater, container, false)
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        val adapter = ExerciseAdapter(object: OnExerciseItemClick {
            override fun onClick(item: ExerciseWithInfo, itemIndex: Int) {
                val action = CreateWorkoutFragmentDirections.toPlanExerciseFragment(item, itemIndex)
                findNavController().navigate(action)
            }
        })

        recycler = binding.rvWorkoutExercises
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnAddExercise.setOnClickListener {
            val action = CreateWorkoutFragmentDirections.toExerciseGuideFragment(true)
            findNavController().navigate(action)
        }

        binding.btnCreateWorkout.setOnClickListener {
            val workout = Workout(binding.etWorkoutName.text.toString(), binding.etWorkoutDescription.text.toString())
            val exercises = exerciseList.map { it.exercise }

            lifecycleScope.launch {
                workoutViewModel.createWorkout(workout, exercises)
                findNavController().popBackStack()
            }
        }

        setFragmentResultListener("ADD_EXERCISE") {_, bundle ->
            bundle.getParcelable<ExerciseWithInfo>("exercise")?.let {
                exerciseList.add(it)
            }
            adapter.exerciseList = exerciseList
        }

        setFragmentResultListener("CONFIG_EXERCISE") {_, bundle ->
            val index = bundle.getInt("exerciseIndex")
            bundle.getParcelable<ExerciseWithInfo>("exercise")?.let {changedExercise ->
                exerciseList[index] = changedExercise
            }

            adapter.exerciseList = exerciseList
        }

        return binding.root
    }
}