package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.databinding.ExerciseItemBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

interface OnExerciseItemClick {
    fun onClick(item: ExerciseWithInfo, itemIndex: Int)
}

class ExerciseAdapter(private val onExerciseItemClick: OnExerciseItemClick?): RecyclerView.Adapter<ExerciseViewHolder>() {
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
        val item = exerciseList[position]
        val binding = ExerciseItemBinding.bind(holder.itemView)

        if (onExerciseItemClick == null) binding.ivExerciseInfoSelect.visibility = View.GONE

        binding.exerciseCard.setOnClickListener {
            onExerciseItemClick?.onClick(item, position)
        }

        holder.bind(item)
    }

}

class ExerciseViewHolder(private val binding: ExerciseItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseWithInfo) {
        binding.tvExerciseInfoName.text = item.exerciseInfo.name

        if (item.exercise.sets + item.exercise.reps + item.exercise.weight == 0) {
            binding.tvExercisePlan.visibility = View.GONE
        } else {
            binding.tvExercisePlan.text = "${item.exercise.sets}x${item.exercise.reps}x${item.exercise.weight}кг"
            binding.tvExercisePlan.visibility = View.VISIBLE
        }

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.PREVIEW_EXERCISE_INFO_IMG}/${item.exerciseInfo.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoPreviewImage)
    }
}