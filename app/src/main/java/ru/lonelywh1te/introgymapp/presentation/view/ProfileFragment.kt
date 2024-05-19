package ru.lonelywh1te.introgymapp.presentation.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentProfileBinding
import ru.lonelywh1te.introgymapp.presentation.viewModel.ProfileFragmentViewModel

class ProfileFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.themeSwitcher.isChecked = viewModel.getTheme()

        binding.themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }

        setProfileData()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        println("pause")
    }

    override fun onResume() {
        super.onResume()
        println("resume")
        setProfileData()
    }



    private fun setProfileData() {
        viewModel.updateUserData()

        binding.etUserName.text = viewModel.userName
        binding.etUserSex.text = if (!viewModel.userSex) "Мужской" else "Женский"
        binding.etUserHeight.text = viewModel.userHeight.toString()
        binding.etUserWeight.text = viewModel.userWeight.toString()

        setCalculatorData()
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.profile_options_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.editProfileData -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
                findNavController().navigate(action)
            }
            else -> findNavController().popBackStack()
        }

        return true
    }
}