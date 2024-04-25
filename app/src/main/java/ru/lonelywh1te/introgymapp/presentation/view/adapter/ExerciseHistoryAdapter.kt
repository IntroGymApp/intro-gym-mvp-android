package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.ExerciseHistoryItemBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory

class ExerciseHistoryAdapter: RecyclerView.Adapter<ExerciseHistoryViewHolder>() {
    var exerciseHistory = mutableListOf<ExerciseHistory>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHistoryViewHolder {
        val binding = ExerciseHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseHistoryViewHolder(binding)
    }

    override fun getItemCount() = exerciseHistory.size

    override fun onBindViewHolder(holder: ExerciseHistoryViewHolder, position: Int) {
        val item = exerciseHistory[position]

        holder.bind(item)
    }
}

class ExerciseHistoryViewHolder(private val binding: ExerciseHistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseHistory) {
        binding.tvExerciseHistoryCount.text = "#" + (absoluteAdapterPosition + 1).toString()
        binding.tvExersiceHistoryInfo.text = "Повторений: ${item.reps} x ${item.weight}кг"
    }
}