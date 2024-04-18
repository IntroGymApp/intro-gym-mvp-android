package ru.lonelywh1te.introgymapp.presentation.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.ActivityExerciseInfoBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.ExerciseInfo

class ExerciseInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseInfoBinding
    private lateinit var exerciseInfo: ExerciseInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseInfoBinding.inflate(layoutInflater)

        exerciseInfo = intent.getSerializableExtra("exerciseInfo") as ExerciseInfo

        setExerciseInfoData()
        setContentView(binding.root)
    }

    private fun setExerciseInfoData() {
        binding.tvExerciseInfoName.text = exerciseInfo.name
        binding.tvExersiceInfoDescription.text = exerciseInfo.description
        binding.tvExecution.text = Html.fromHtml(exerciseInfo.execution)
        binding.tvAdvices.text = Html.fromHtml(exerciseInfo.advices)

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.EXERCISE_INFO_IMG}/${exerciseInfo.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoImage)
    }
}