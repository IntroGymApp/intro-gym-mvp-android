package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentGetNameBinding

class GetNameFragment : Fragment() {
    private lateinit var binding: FragmentGetNameBinding

    private var userName: String = ""
    private var userSex: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGetNameBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            saveData()

            val action = GetNameFragmentDirections.toGetActivityLevelFragment()
            findNavController().navigate(action)
        }

        binding.btnMale.setOnClickListener {
            userSex = false
            setUserSex()

        }

        binding.btnFemale.setOnClickListener {
            userSex = true
            setUserSex()
        }

        setUserSex()
        return binding.root
    }

    private fun setUserSex() {
        if (!userSex) {
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

    private fun saveData() {
        val data = requireActivity().getSharedPreferences(Constants.USER_DATA_KEY, MODE_PRIVATE)
        userName = binding.etUserName.text.toString()

        if (userName.isNotEmpty()) data.edit().putString(Constants.USER_NAME_KEY, userName).apply()
        data.edit().putBoolean(Constants.USER_SEX_KEY, userSex).apply()
    }
}