package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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
        set(newList) {
            val diffCallback = ExerciseGroupCallback(exerciseGroupList, newList)
            val diffGroupList = DiffUtil.calculateDiff(diffCallback)

            field = newList

            diffGroupList.dispatchUpdatesTo(this)
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

    class ExerciseGroupCallback(private val oldList: List<ExerciseGroup>, private val newList: List<ExerciseGroup>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.id == newItem.id && oldItem.img == newItem.img && oldItem.name == newItem.name && oldItem.count == newItem.count && oldItem.textId == newItem.textId
        }
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


