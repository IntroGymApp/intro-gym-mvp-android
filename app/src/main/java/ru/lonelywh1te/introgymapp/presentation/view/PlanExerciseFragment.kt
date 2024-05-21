package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.lonelywh1te.introgymapp.databinding.FragmentPlanExerciseBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

class PlanExerciseFragment : Fragment() {
    private lateinit var binding: FragmentPlanExerciseBinding
    private lateinit var exerciseWithInfo: ExerciseWithInfo
    private val args: PlanExerciseFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlanExerciseBinding.inflate(inflater, container, false)
        exerciseWithInfo = args.exerciseWithInfo

        setExerciseData(exerciseWithInfo)

        binding.btnSaveExercise.setOnClickListener {
            val sets = if (binding.etSets.text.toString().isNotEmpty()) binding.etSets.text.toString().toInt() else exerciseWithInfo.exercise.sets
            val reps = if (binding.etReps.text.toString().isNotEmpty()) binding.etReps.text.toString().toInt() else exerciseWithInfo.exercise.reps
            val weight = if (binding.etWeight.text.toString().isNotEmpty()) binding.etWeight.text.toString().toFloat() else exerciseWithInfo.exercise.weight
            val note = binding.etNote.text.toString().ifEmpty { null }

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

    private fun setExerciseData(exerciseWithInfo: ExerciseWithInfo) {
        binding.etSets.setHint(exerciseWithInfo.exercise.sets.toString())
        binding.etReps.setHint(exerciseWithInfo.exercise.reps.toString())
        binding.etWeight.setHint(exerciseWithInfo.exercise.weight.toString())
        if (exerciseWithInfo.exercise.note != null) binding.etNote.setText(exerciseWithInfo.exercise.note)
    }
}