package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.WorkoutItemBinding
import ru.lonelywh1te.introgymapp.domain.model.Workout

interface OnWorkoutItemClick {
    fun onClick(item: Workout)
}

class WorkoutAdapter(private val onWorkoutItemClick: OnWorkoutItemClick): RecyclerView.Adapter<WorkoutViewHolder>() {
    var workoutList = listOf<Workout>()
        set(newList) {
            val diffCallback = WorkoutCallback(workoutList, newList)
            val diffWorkout = DiffUtil.calculateDiff(diffCallback)

            field = newList

            diffWorkout.dispatchUpdatesTo(this)
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
        binding.tvExerciseCount.text = "Упражнения: ${item.exerciseCount}"
    }
}

class WorkoutCallback(private val oldList: List<Workout>, private val newList: List<Workout>): DiffUtil.Callback(){
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
                oldItem.name == newItem.name &&
                oldItem.description == newItem.description &&
                oldItem.date == newItem.date &&
                oldItem.exerciseCount == newItem.exerciseCount
    }
}