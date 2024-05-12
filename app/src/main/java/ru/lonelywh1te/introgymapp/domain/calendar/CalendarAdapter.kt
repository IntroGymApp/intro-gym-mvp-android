package ru.lonelywh1te.introgymapp.domain.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
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
            binding.dayItemCard.setCardBackgroundColor(MaterialColors.getColor(binding.dayItemCard, R.attr.ig_primaryColor))
        } else {
            binding.dayItemCard.setCardBackgroundColor(MaterialColors.getColor(binding.dayItemCard, R.attr.ig_primaryLightColor))
        }
    }
}