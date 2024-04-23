package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutViewBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

class WorkoutViewFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentWorkoutViewBinding
    private lateinit var recycler: RecyclerView
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var workoutViewModel: WorkoutViewModel
    private val args: WorkoutViewFragmentArgs by navArgs()

    private var workout: Workout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workout = args.workout
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        binding = FragmentWorkoutViewBinding.inflate(inflater, container, false)

        val adapter = ExerciseAdapter(null)

        recycler = binding.rvWorkoutExercises
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        workoutViewModel.currentWorkout.observe(viewLifecycleOwner) {
            workout = it
            setWorkoutData()
        }

        exerciseViewModel.exerciseWithInfoList.observe(viewLifecycleOwner) {
            adapter.exerciseList = it
        }

        setWorkoutData()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        workoutViewModel.getWorkoutById(args.workout.id)
        exerciseViewModel.getAllExercisesWithInfoByWorkoutId(args.workout.id)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.workout_options_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.editWorkout -> editWorkout()
            R.id.deleteWorkout -> deleteWorkout()
            else -> findNavController().popBackStack()
        }

        return true
    }

    private fun editWorkout() {
        workout?.let {
            val action = WorkoutViewFragmentDirections.toCreateEditWorkoutFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun deleteWorkout() {
        workout?.let {
            workoutViewModel.deleteWorkout(it)
            findNavController().popBackStack()
        }
    }

    private fun setWorkoutData() {
        workout?.let {
            (activity as? AppCompatActivity)?.supportActionBar?.title = it.name
            if (it.description == "") {
                binding.workoutDescriptionLayout.visibility = View.GONE
            } else {
                binding.workoutDescriptionLayout.visibility = View.VISIBLE
            }

            binding.tvWorkoutDescription.text = it.description
        }
    }
}