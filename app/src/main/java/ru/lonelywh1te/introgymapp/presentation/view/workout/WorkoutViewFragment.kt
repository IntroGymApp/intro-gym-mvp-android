package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutViewBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseItemClick
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewFragmentViewModel

class WorkoutViewFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentWorkoutViewBinding
    private lateinit var recycler: RecyclerView
    private lateinit var viewModel: WorkoutViewFragmentViewModel
    private val args: WorkoutViewFragmentArgs by navArgs()

    private var workout: Workout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        binding = FragmentWorkoutViewBinding.inflate(inflater, container, false)

        val adapter = ExerciseAdapter(if (!args.executionMode) null else object: OnExerciseItemClick {
            override fun onClick(item: ExerciseWithInfo, itemIndex: Int) {
                val action = WorkoutViewFragmentDirections.toExerciseExecuteFragment(item, item.exerciseInfo.name, workout?.date ?: 0L)
                findNavController().navigate(action)
            }
        })

        recycler = binding.rvWorkoutExercises
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.workout.observe(viewLifecycleOwner) {
            workout = it
            setWorkoutData()
        }

        viewModel.exerciseList.observe(viewLifecycleOwner) {
            adapter.exerciseList = it
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWorkoutById(args.workoutId)
        viewModel.getExercisesWithInfoByWorkoutId(args.workoutId)
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
        val action = WorkoutViewFragmentDirections.toCreateEditWorkoutFragment(workout?.id ?: 0)
        findNavController().navigate(action)

    }

    private fun deleteWorkout() {
        workout?.let {
            viewModel.deleteWorkout(it)
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