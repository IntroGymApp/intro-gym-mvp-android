package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.databinding.ExerciseGroupItemBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel

interface OnExerciseGroupItemClick {
    fun onClick(item: ExerciseGroup)
}

class ExerciseGroupAdapter(private val onExerciseGroupItemClick: OnExerciseGroupItemClick): RecyclerView.Adapter<ExerciseGroupViewHolder>() {
    var exerciseGroupList = listOf<ExerciseGroup>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseGroupViewHolder {
        val binding = ExerciseGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseGroupViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseGroupList.size

    override fun onBindViewHolder(holder: ExerciseGroupViewHolder, position: Int) {
        val item = exerciseGroupList[position]
        val binding = ExerciseGroupItemBinding.bind(holder.itemView)

        binding.exerciseGroupCard.setOnClickListener {
            onExerciseGroupItemClick.onClick(item)
        }

        holder.bind(item)
    }
}

class ExerciseGroupViewHolder(private val binding: ExerciseGroupItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseGroup) {
        binding.tvGroupName.text = item.name
        binding.tvExerciseCount.text = "Упражнения: ${item.count}"

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.EXERCISE_GROUP_IMG}/${item.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivGroupImage)
    }
}


