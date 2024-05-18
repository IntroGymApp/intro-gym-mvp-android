package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentGetActivityLevelBinding

class GetActivityLevelFragment : Fragment() {
    private lateinit var binding: FragmentGetActivityLevelBinding
    private var userActivityLevel = 1.2f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGetActivityLevelBinding.inflate(inflater, container, false)
        val spinner = binding.activityLevelSpinner
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.activity_levels, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_item)

        spinner.adapter = adapter

        binding.activityLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setActivityLevel(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        binding.btnNext.setOnClickListener {
            saveData()

            val action = GetActivityLevelFragmentDirections.toGetWeightFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun setActivityLevel(position: Int) {
        // TODO: подобрать более точные значения

        when(position) {
            0 -> userActivityLevel = 1.2f
            1 -> userActivityLevel = 1.375f
            2 -> userActivityLevel = 1.5f
            3 ->  userActivityLevel = 1.6f
            4 -> userActivityLevel = 1.8f
        }
    }

    private fun saveData() {
        val data = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, MODE_PRIVATE)
        data.edit().putFloat(Constants.USER_ACTIVITY_LEVEL_KEY, userActivityLevel).apply()
    }
}