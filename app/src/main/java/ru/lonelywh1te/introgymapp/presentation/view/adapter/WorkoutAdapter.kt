package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.WorkoutItemBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout

interface OnWorkoutItemClick {
    fun onClick(item: Workout)
}

class WorkoutAdapter(private val onWorkoutItemClick: OnWorkoutItemClick): RecyclerView.Adapter<WorkoutViewHolder>() {
    var workoutList = listOf<Workout>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = WorkoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun getItemCount(): Int = workoutList.size


    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val item = workoutList[position]
        val binding = WorkoutItemBinding.bind(holder.itemView)

        binding.workoutCard.setOnClickListener {
            onWorkoutItemClick.onClick(item)
        }

        holder.bind(item)
    }
}

class WorkoutViewHolder(private val binding: WorkoutItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Workout) {
        binding.tvWorkoutName.text = item.name
    }
}