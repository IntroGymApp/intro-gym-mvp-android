package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.databinding.FragmentGetBirthdayBinding
import java.time.LocalDate

class GetBirthdayFragment : Fragment() {
    private lateinit var binding: FragmentGetBirthdayBinding
    private var currentDate = LocalDate.now()
    private var userBirthday: Long = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGetBirthdayBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            currentDate = currentDate
                .withDayOfMonth(binding.datePicker.dayOfMonth)
                .withMonth(binding.datePicker.month + 1)
                .withYear(binding.datePicker.year)

            saveData()

            val action = GetBirthdayFragmentDirections.toFinishFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun saveData() {
        val data = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, MODE_PRIVATE)
        userBirthday = currentDate.toEpochDay() * 86400000L

        data.edit().putLong(Constants.USER_BIRTHDAY_KEY, userBirthday).apply()
    }
}