package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnWorkoutItemClick
import ru.lonelywh1te.introgymapp.presentation.view.adapter.WorkoutAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.println(Log.DEBUG, "WorkoutFragment", "ON_CREATE")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.println(Log.DEBUG, "WorkoutFragment", "ON_CREATE_VIEW")
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        val adapter = WorkoutAdapter(object: OnWorkoutItemClick {
            override fun onClick(item: Workout) {
                TODO("Not yet implemented")
            }
        })

        workoutViewModel.workoutList.observe(viewLifecycleOwner) {
            adapter.workoutList = it
            Log.println(Log.DEBUG, "WorkoutFragment", "$it")
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