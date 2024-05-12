package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.ExerciseInfoItemBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo

interface OnExerciseInfoItemClick {
    fun onClick(item: ExerciseInfo)
}

class ExerciseInfoAdapter(private val onExerciseInfoItemClick: OnExerciseInfoItemClick, private val pickMode: Boolean): RecyclerView.Adapter<ExerciseInfoViewHolder>() {
    var exerciseInfoList = listOf<ExerciseInfo>()
        set(newList) {
            val diffCallback = ExerciseInfoCallback(exerciseInfoList, newList)
            val diffExerciseInfo = DiffUtil.calculateDiff(diffCallback)

            field = newList

            diffExerciseInfo.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseInfoViewHolder {
        val binding = ExerciseInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseInfoList.size


    override fun onBindViewHolder(holder: ExerciseInfoViewHolder, position: Int) {
        val item = exerciseInfoList[position]
        val binding = ExerciseInfoItemBinding.bind(holder.itemView)

        binding.exerciseInfoCard.setOnClickListener {
            onExerciseInfoItemClick.onClick(item)
        }

        holder.bind(item, pickMode)
    }

    class ExerciseInfoCallback(private val oldList: List<ExerciseInfo>, private val newList: List<ExerciseInfo>): DiffUtil.Callback(){
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

            return oldItem.id == newItem.id &&
                    oldItem.img == newItem.img &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.advices == newItem.description &&
                    oldItem.execution == newItem.description &&
                    oldItem.preparation == newItem.preparation &&
                    oldItem.groupId == newItem.groupId
        }
    }
}

class ExerciseInfoViewHolder(private val binding: ExerciseInfoItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseInfo, pickMode: Boolean) {
        binding.tvExerciseInfoName.text = item.name

        if (pickMode) {
            binding.ivExerciseInfoSelect.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_add))
        } else {
            binding.ivExerciseInfoSelect.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_arrow_right))
        }

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.PREVIEW_EXERCISE_INFO_IMG}/${item.img}")))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivExerciseInfoPreviewImage)
    }
}