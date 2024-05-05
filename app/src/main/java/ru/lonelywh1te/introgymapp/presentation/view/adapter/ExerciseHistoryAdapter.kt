package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.ExerciseHistoryItemBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory

class ExerciseHistoryAdapter: RecyclerView.Adapter<ExerciseHistoryViewHolder>() {
    var exerciseHistory = mutableListOf<ExerciseHistory>()
        set(newList) {
            val diffCallback = ExerciseHistoryCallback(exerciseHistory, newList)
            val diffExerciseHistory = DiffUtil.calculateDiff(diffCallback)

            field = newList

            diffExerciseHistory.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHistoryViewHolder {
        val binding = ExerciseHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseHistoryViewHolder(binding)
    }

    override fun getItemCount() = exerciseHistory.size

    override fun onBindViewHolder(holder: ExerciseHistoryViewHolder, position: Int) {
        val item = exerciseHistory[position]

        holder.bind(item, itemCount)
    }

    class ExerciseHistoryCallback(private val oldList: List<ExerciseHistory>, private val newList: List<ExerciseHistory>): DiffUtil.Callback(){
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

            return oldItem.id == newItem.id && oldItem.reps == newItem.reps && oldItem.exerciseId == newItem.exerciseId && oldItem.weight == newItem.weight && oldItem.date == newItem.date
        }
    }
}

class ExerciseHistoryViewHolder(private val binding: ExerciseHistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseHistory, itemCount: Int) {
        binding.tvExerciseHistoryCount.text = "#" + (itemCount - absoluteAdapterPosition).toString()
        binding.tvExersiceHistoryInfo.text = "Повторений: ${item.reps} x ${item.weight}кг"
    }
}