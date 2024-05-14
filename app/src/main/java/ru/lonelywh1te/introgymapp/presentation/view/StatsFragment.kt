package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.color.MaterialColors
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentStatsBinding
import ru.lonelywh1te.introgymapp.presentation.viewModel.StatsFragmentViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private lateinit var viewModel: StatsFragmentViewModel
    private lateinit var weightStats: LineChart
    private var currentDate: LocalDate = LocalDate.now()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        viewModel = getViewModel()

        weightStats = binding.weightStats
        styleWeightStats()

        viewModel.exerciseHistory.observe(viewLifecycleOwner) {
            setWeightStats()
            weightStats.invalidate()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExerciseHistoryByPeriod(currentDate.withDayOfMonth(1).toEpochDay() * 86400000L, currentDate.withDayOfMonth(currentDate.lengthOfMonth()).toEpochDay() * 86400000L)
    }

    private fun setWeightStats() {
        val entries = viewModel.getEntriesByCurrentMonth()
        if (entries.isEmpty()) {
            weightStats.setNoDataText("Нет данных")
            weightStats.setNoDataTextColor(MaterialColors.getColor(weightStats, R.attr.ig_primaryColor))
            return
        }

        val dataSet = getWeightLineDataSet(viewModel.getEntriesByCurrentMonth())
        weightStats.data = LineData(dataSet)
    }

    private fun styleWeightStats() {
        val xAxisLabels = viewModel.getXAxisCurrentMonthLabels()

        weightStats.setBackgroundColor(MaterialColors.getColor(binding.weightStats, R.attr.ig_cardBackgroundColor))
        weightStats.setNoDataText("")
        weightStats.xAxis.textColor = MaterialColors.getColor(binding.weightStats, R.attr.ig_defaultTextColor)
        weightStats.xAxis.position = XAxis.XAxisPosition.BOTTOM
        weightStats.xAxis.isGranularityEnabled = true
        weightStats.xAxis.granularity = 1f
        weightStats.axisLeft.axisMinimum = 0f
        weightStats.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return xAxisLabels[value.toInt()]
            }
        }
        weightStats.axisLeft.isEnabled = false
        weightStats.axisRight.isEnabled = false
        weightStats.legend.isEnabled = false
        weightStats.description.isEnabled = false
        weightStats.setExtraOffsets(25f, 20f, 25f, 20f)
    }

    private fun getWeightLineDataSet(entries: List<Entry>): LineDataSet {
        val dataSet = LineDataSet(entries, "Weight")

        dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        dataSet.cubicIntensity = 0.2f
        dataSet.color = MaterialColors.getColor(binding.weightStats, R.attr.ig_primaryColor)
        dataSet.setCircleColor(MaterialColors.getColor(binding.weightStats, R.attr.ig_primaryLightColor))
        dataSet.valueTextColor = MaterialColors.getColor(binding.weightStats, R.attr.ig_defaultTextColor)

        return dataSet
    }
}