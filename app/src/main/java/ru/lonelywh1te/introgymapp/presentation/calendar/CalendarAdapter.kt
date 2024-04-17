package ru.lonelywh1te.introgymapp.presentation.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.DayItemBinding

interface OnItemClickListener {
    fun onDayItemClick(item: Day)
}

class CalendarAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<CalendarViewHolder>() {
    var week = listOf<Day>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = DayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = week.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = week[position]
        val binding = DayItemBinding.bind(holder.itemView)

        binding.dayItemCard.setOnClickListener {
            onItemClickListener.onDayItemClick(item)
        }

        holder.bind(item)
    }

}

class CalendarViewHolder(private val binding: DayItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Day) {
        binding.tvDayOfWeek.text = item.dayOfWeek
        binding.tvNumber.text = item.dayOfMouth.toString()

        if (item.isSelected) {
            binding.dayItemCard.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.primary_color))
        } else {
            binding.dayItemCard.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.primary_light_color))
        }

        Log.println(Log.DEBUG, "IntroGymApp", "$item\n")
    }
}