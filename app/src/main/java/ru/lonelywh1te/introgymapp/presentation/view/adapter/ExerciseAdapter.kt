package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.databinding.ExerciseItemBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

class ExerciseAdapter: RecyclerView.Adapter<ExerciseViewHolder>() {
    var exerciseList = listOf<ExerciseWithInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]

        holder.bind(exercise)
    }

}

class ExerciseViewHolder(private val binding: ExerciseItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseWithInfo) {
        binding.tvExerciseInfoName.text = item.exerciseInfo.name

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.PREVIEW_EXERCISE_INFO_IMG}/${item.exerciseInfo.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoPreviewImage)
    }
}