package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentGetWeightBinding

class GetWeightFragment : Fragment() {
    private lateinit var binding: FragmentGetWeightBinding
    private var userWeight = 0
    private var userHeight = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGetWeightBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            saveData()

            val action = GetWeightFragmentDirections.toGetGoalFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun saveData() {
        val data = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, MODE_PRIVATE)
        if (binding.etUserHeight.text.isNotEmpty()) userHeight = binding.etUserHeight.text.toString().toInt()
        if (binding.etUserWeight.text.isNotEmpty()) userWeight = binding.etUserWeight.text.toString().toInt()

        data.edit().putInt(Constants.USER_WEIGHT_KEY, userWeight).apply()
        data.edit().putInt(Constants.USER_HEIGHT_KEY, userHeight).apply()
    }
}