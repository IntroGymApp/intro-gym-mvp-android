package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentEditProfileBinding
import ru.lonelywh1te.introgymapp.presentation.viewModel.ProfileFragmentViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        binding.btnMale.setOnClickListener {
            viewModel.userSex = false
            setUserSex()
        }

        binding.btnFemale.setOnClickListener {
            viewModel.userSex = true
            setUserSex()
        }

        binding.btnWeightLoss.setOnClickListener {
            viewModel.userGoal = Constants.WEIGHT_LOSS_GOAL
            setGoal()
        }

        binding.btnWeightSupport.setOnClickListener {
            viewModel.userGoal = Constants.WEIGHT_SUPPORT_GOAL
            setGoal()
        }

        binding.btnWeightGain.setOnClickListener {
            viewModel.userGoal = Constants.WEIGHT_GAIN_GOAL
            setGoal()
        }

        binding.btnUserBirthday.setOnClickListener {
            showHideDatePicker()
        }

        binding.btnSaveUserData.setOnClickListener {
            viewModel.userName = binding.etUserName.text.toString()
            viewModel.userHeight = binding.etUserHeight.text.toString().toInt()
            viewModel.userWeight = binding.etUserWeight.text.toString().toInt()

            viewModel.saveUserData()
            findNavController().popBackStack()
        }

        setUserData()
        return binding.root
    }

    private fun setUserData() {
        setUserSex()
        setActivityLevel()
        setGoal()
        setUserBirthday()

        binding.etUserName.setText(viewModel.userName.toString())
        binding.etUserHeight.setText(viewModel.userHeight.toString())
        binding.etUserWeight.setText(viewModel.userWeight.toString())
    }

    private fun setUserSex() {
        if (!viewModel.userSex) {
            binding.btnMale.background.setTint(MaterialColors.getColor(binding.btnMale, R.attr.ig_primaryColor))
            binding.btnMale.setColorFilter(MaterialColors.getColor(binding.btnMale, R.attr.ig_backgroundColor))

            binding.btnFemale.background.setTint(MaterialColors.getColor(binding.btnFemale, R.attr.ig_cardBackgroundColor))
            binding.btnFemale.setColorFilter(MaterialColors.getColor(binding.btnFemale, R.attr.ig_defaultTextColor))
        } else {
            binding.btnFemale.background.setTint(MaterialColors.getColor(binding.btnFemale, R.attr.ig_primaryColor))
            binding.btnFemale.setColorFilter(MaterialColors.getColor(binding.btnFemale, R.attr.ig_backgroundColor))

            binding.btnMale.background.setTint(MaterialColors.getColor(binding.btnMale, R.attr.ig_cardBackgroundColor))
            binding.btnMale.setColorFilter(MaterialColors.getColor(binding.btnMale, R.attr.ig_defaultTextColor))
        }
    }

    private fun setGoal() {
        val selectedButton: TextView = when(viewModel.userGoal) {
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

    private fun setActivityLevel() {
        val spinner = binding.activityLevelSpin
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.activity_levels, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.setSelection(when(viewModel.userActivityLevel) {
            1.2f -> 0
            1.375f -> 1
            1.5f -> 2
            1.6f -> 3
            1.8f -> 4
            else -> 0
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> viewModel.userActivityLevel = 1.2f
                    1 -> viewModel.userActivityLevel = 1.375f
                    2 -> viewModel.userActivityLevel = 1.5f
                    3 ->  viewModel.userActivityLevel = 1.6f
                    4 -> viewModel.userActivityLevel = 1.8f
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
    }

    private fun setUserBirthday() {
        val localDate = viewModel.getUserBirthday()
        val formatDate = "${localDate.dayOfMonth} ${localDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ${localDate.year}г"
        binding.btnUserBirthday.text = formatDate
    }

    private fun showHideDatePicker() {
        if (binding.datePicker.visibility == View.GONE) {
            val localDate = viewModel.getUserBirthday()
            binding.datePicker.updateDate(localDate.year, localDate.month.value - 1, localDate.dayOfMonth)

            binding.datePicker.visibility = View.VISIBLE

            binding.btnUserBirthday.background.setTint(MaterialColors.getColor(binding.btnUserBirthday, R.attr.ig_primaryColor))
            binding.btnUserBirthday.setTextColor(MaterialColors.getColor(binding.btnUserBirthday, R.attr.ig_lightTextColor))
            binding.btnUserBirthday.text = "Выбрать"
        } else {
            val date = LocalDate.now()
                .withDayOfMonth(binding.datePicker.dayOfMonth)
                .withMonth(binding.datePicker.month + 1)
                .withYear(binding.datePicker.year)

            viewModel.userBirthday = date.toEpochDay() * 86400000L
            setUserBirthday()

            binding.datePicker.visibility = View.GONE
            binding.btnUserBirthday.background.setTint(MaterialColors.getColor(binding.btnUserBirthday, R.attr.ig_cardBackgroundColor))
            binding.btnUserBirthday.setTextColor(MaterialColors.getColor(binding.btnUserBirthday, R.attr.ig_defaultTextColor))
        }
    }
}