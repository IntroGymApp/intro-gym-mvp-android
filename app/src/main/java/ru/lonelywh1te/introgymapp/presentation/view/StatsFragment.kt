package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.color.MaterialColors
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // TODO: подгрузить историю тренировок
        binding = FragmentStatsBinding.inflate(inflater, container, false)

        val stats = binding.weightStats

        val entries = mutableListOf<Entry>()

        val labels = mutableListOf<String>()

        for(i in 0..30){
            labels.add("$i мая")
            entries.add(Entry(i.toFloat(), (0..30).random().toFloat()))
        }

        val dataSet = LineDataSet(entries, "test")
        dataSet.color = MaterialColors.getColor(binding.weightStats, R.attr.ig_primaryLightColor)
        dataSet.setCircleColor(MaterialColors.getColor(binding.weightStats, R.attr.ig_primaryColor))
        dataSet.valueTextColor = MaterialColors.getColor(binding.weightStats, R.attr.ig_defaultTextColor)

        val barData = LineData(dataSet)
        stats.data = barData
        stats.setBackgroundColor(MaterialColors.getColor(binding.weightStats, R.attr.ig_cardBackgroundColor))
        stats.xAxis.textColor = MaterialColors.getColor(binding.weightStats, R.attr.ig_defaultTextColor)
        stats.xAxis.position = XAxis.XAxisPosition.BOTTOM
        stats.setScaleMinima(1f, 0f)
        stats.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // value is x as index
                return labels[value.toInt()]
            }
        }
        stats.axisLeft.isEnabled = false
        stats.axisRight.isEnabled = false
        stats.legend.isEnabled = false
        stats.description.isEnabled = false
        stats.setExtraOffsets(25f, 20f, 25f, 20f)
        stats.invalidate()

        return binding.root
    }
}