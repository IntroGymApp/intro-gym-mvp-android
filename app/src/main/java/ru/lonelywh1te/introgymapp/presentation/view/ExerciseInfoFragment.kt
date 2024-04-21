package ru.lonelywh1te.introgymapp.presentation.view

import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.databinding.FragmentExerciseInfoBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo

class ExerciseInfoFragment : Fragment() {
    private val args: ExerciseInfoFragmentArgs by navArgs()
    private lateinit var exerciseInfo: ExerciseInfo

    private lateinit var binding: FragmentExerciseInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExerciseInfoBinding.inflate(inflater, container, false)
        exerciseInfo = args.exerciseInfo

        setExerciseInfoData()
        return binding.root
    }

    private fun setExerciseInfoData() {
        binding.tvExersiceInfoDescription.text = exerciseInfo.description
        binding.tvExecution.text = Html.fromHtml(exerciseInfo.execution)
        binding.tvAdvices.text = Html.fromHtml(exerciseInfo.advices)

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.EXERCISE_INFO_IMG}/${exerciseInfo.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoImage)
    }
}