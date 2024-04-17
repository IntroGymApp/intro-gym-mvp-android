package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.lonelywh1te.introgymapp.databinding.ExerciseGroupItemBinding
import ru.lonelywh1te.introgymapp.domain.ExerciseGroup

class ExerciseGroupAdapter: RecyclerView.Adapter<ExerciseGroupViewHolder>() {
    var list = listOf<ExerciseGroup>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseGroupViewHolder {
        val binding = ExerciseGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseGroupViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ExerciseGroupViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item)
    }
}

class ExerciseGroupViewHolder(private val binding: ExerciseGroupItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseGroup) {
        binding.tvGroupName.text = item.name
        binding.tvExerciseCount.text = "Упражнения: ${item.count}"

        val imagesAssetsPath = "file:///android_asset/"
        Glide.with(binding.root).load((Uri.parse(imagesAssetsPath + item.imageAssetPath))).into(binding.ivGroupImage)
    }
}


