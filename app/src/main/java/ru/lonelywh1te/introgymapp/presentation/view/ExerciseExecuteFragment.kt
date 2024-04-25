package ru.lonelywh1te.introgymapp.presentation.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentExerciseExecuteBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

class ExerciseExecuteFragment : Fragment() {
    private lateinit var binding: FragmentExerciseExecuteBinding
    private lateinit var exerciseWithInfo: ExerciseWithInfo
    private val args: ExerciseExecuteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseWithInfo = args.exerciseWithInfo
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExerciseExecuteBinding.inflate(layoutInflater)

        setExerciseData()
        return binding.root
    }

    private fun setExerciseData() {
        if (!exerciseWithInfo.exercise.note.isNullOrEmpty()) {
            binding.tvExerciseNote.text = exerciseWithInfo.exercise.note
            binding.exerciseDescriptionLayout.visibility = View.VISIBLE
        } else {
            binding.exerciseDescriptionLayout.visibility = View.GONE
        }

        binding.tvExerciseSets.text = "0 / " + exerciseWithInfo.exercise.sets.toString()
        binding.tvExerciseReps.text = exerciseWithInfo.exercise.reps.toString()
        binding.tvExerciseWeight.text = exerciseWithInfo.exercise.weight.toString()

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.EXERCISE_INFO_IMG}/${exerciseWithInfo.exerciseInfo.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoImage)
    }
}