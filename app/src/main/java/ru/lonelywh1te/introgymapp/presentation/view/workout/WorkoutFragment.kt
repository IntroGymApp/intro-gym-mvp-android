package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnWorkoutItemClick
import ru.lonelywh1te.introgymapp.presentation.view.adapter.WorkoutAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var recycler: RecyclerView

    private val args: WorkoutFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        val adapter = WorkoutAdapter(object: OnWorkoutItemClick {
            override fun onClick(item: Workout) {
                if (args.pickMode) {
                    lifecycleScope.launch {
                        workoutViewModel.addWorkoutDate(item, args.date)
                        findNavController().popBackStack()
                    }
                } else {
                    val action = WorkoutFragmentDirections.toWorkoutViewFragment(item)
                    findNavController().navigate(action)
                }
            }
        })

        workoutViewModel.workoutList.observe(viewLifecycleOwner) {
            adapter.workoutList = it
        }

        recycler = binding.rvWorkouts
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnCreateWorkout.setOnClickListener {
            val action = WorkoutFragmentDirections.toCreateWorkout()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        workoutViewModel.getAllWorkouts()
    }
}