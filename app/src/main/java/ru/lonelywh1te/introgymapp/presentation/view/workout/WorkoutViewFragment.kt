package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutViewBinding
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseInfoViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel

class WorkoutViewFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutViewBinding
    private lateinit var recycler: RecyclerView
    private lateinit var exerciseViewModel: ExerciseViewModel

    private val args: WorkoutViewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWorkoutViewBinding.inflate(inflater, container, false)
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        val adapter = ExerciseAdapter(null)

        recycler = binding.rvWorkoutExercises
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.tvWorkoutDescription.text = args.workout.description

        exerciseViewModel.exerciseWithInfoList.observe(viewLifecycleOwner) {
            adapter.exerciseList = it
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        exerciseViewModel.getAllExercisesWithInfoByWorkoutId(args.workout.id)
    }
}