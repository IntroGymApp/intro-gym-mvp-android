package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentGetGoalBinding

class GetGoalFragment : Fragment() {
    private lateinit var binding: FragmentGetGoalBinding
    private var userGoal: Int = Constants.WEIGHT_LOSS_GOAL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGetGoalBinding.inflate(inflater, container, false)

        binding.btnWeightLoss.setOnClickListener {
            userGoal = Constants.WEIGHT_LOSS_GOAL
            setGoal()
        }

        binding.btnWeightSupport.setOnClickListener {
            userGoal = Constants.WEIGHT_SUPPORT_GOAL
            setGoal()
        }

        binding.btnWeightGain.setOnClickListener {
            userGoal = Constants.WEIGHT_GAIN_GOAL
            setGoal()
        }

        binding.btnNext.setOnClickListener {
            saveData()

            val action = GetGoalFragmentDirections.toGetBirthdayFragment()
            findNavController().navigate(action)
        }

        setGoal()
        return binding.root
    }

    private fun setGoal() {
        val selectedButton: TextView = when(userGoal) {
            Constants.WEIGHT_LOSS_GOAL -> binding.btnWeightLoss
            Constants.WEIGHT_SUPPORT_GOAL -> binding.btnWeightSupport
            Constants.WEIGHT_GAIN_GOAL -> binding.btnWeightGain
            else -> binding.btnWeightLoss
        }

        for (button in listOf(binding.btnWeightLoss, binding.btnWeightSupport, binding.btnWeightGain)) {
            button.background.setTint(MaterialColors.getColor(button, R.attr.ig_cardBackgroundColor))
            button.setTextColor(MaterialColors.getColor(button, R.attr.ig_defaultTextColor))
        }

        selectedButton.background.setTint(MaterialColors.getColor(binding.btnWeightLoss, R.attr.ig_primaryColor))
        selectedButton.setTextColor(MaterialColors.getColor(binding.btnWeightLoss, R.attr.ig_backgroundColor))
    }

    private fun saveData() {
        val data = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, MODE_PRIVATE)
        data.edit().putInt(Constants.USER_GOAL_KEY, userGoal).apply()
    }
}