package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentPlanExerciseBinding
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

class PlanExerciseFragment : Fragment() {
    private lateinit var binding: FragmentPlanExerciseBinding
    private lateinit var exerciseWithInfo: ExerciseWithInfo
    private val args: PlanExerciseFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlanExerciseBinding.inflate(inflater, container, false)
        exerciseWithInfo = args.exerciseWithInfo

        binding.btnSaveExercise.setOnClickListener {
            val sets = binding.etSets.text.toString().toInt()
            val reps = binding.etReps.text.toString().toInt()
            val weight = binding.etWeight.text.toString().toInt()
            val note = binding.etNote.text.toString()

            val exercise = exerciseWithInfo.exercise.copy(sets = sets, reps = reps, weight = weight, note = note)
            exerciseWithInfo = exerciseWithInfo.copy(exercise = exercise, exerciseInfo = exerciseWithInfo.exerciseInfo)

            val bundle = Bundle().apply {
                putParcelable("exercise", exerciseWithInfo)
                putInt("exerciseIndex", args.index)
            }

            setFragmentResult("CONFIG_EXERCISE", bundle)
            findNavController().popBackStack()
        }




        return binding.root
    }
}