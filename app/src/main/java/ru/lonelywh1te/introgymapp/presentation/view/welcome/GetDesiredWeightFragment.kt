package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentGetDesiredWeightBinding

class GetDesiredWeightFragment : Fragment() {
    private lateinit var binding: FragmentGetDesiredWeightBinding
    private var userDesiredWeight = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGetDesiredWeightBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            saveData()

            val action = GetDesiredWeightFragmentDirections.toGetBirthdayFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun saveData() {
        val data = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, Context.MODE_PRIVATE)
        userDesiredWeight = binding.etUserDesiredWeight.text.toString().toInt()

        data.edit().putInt(Constants.USER_DESIRED_WEIGHT_KEY, userDesiredWeight).apply()
    }
}