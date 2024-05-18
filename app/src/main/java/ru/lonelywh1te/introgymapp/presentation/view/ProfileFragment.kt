package ru.lonelywh1te.introgymapp.presentation.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.databinding.FragmentProfileBinding
import ru.lonelywh1te.introgymapp.presentation.viewModel.ProfileFragmentViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.themeSwitcher.isChecked = viewModel.getTheme()

        binding.themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }

        setUserData()
        setCalculatorData()
        return binding.root
    }



    private fun setUserData() {
        binding.etUserName.text = viewModel.userName
        binding.etUserSex.text = if (!viewModel.userSex) "Мужской" else "Женский"
        binding.etUserHeight.text = viewModel.userHeight.toString()
        binding.etUserWeight.text = viewModel.userWeight.toString()
    }

    private fun setCalculatorData() {
        binding.tvBMI.text = viewModel.getBMI()
        binding.tvIdealWeight.text = viewModel.getIdealWeight()

        binding.tvKcal.text = viewModel.calories.toString()
        binding.tvProtein.text = viewModel.proteins.toString()
        binding.tvFat.text = viewModel.fats.toString()
        binding.tvCarbohydrate.text = viewModel.carbohydrates.toString()

        println(viewModel.getUserAge())
    }
}