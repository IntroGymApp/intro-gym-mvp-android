package ru.lonelywh1te.introgymapp.presentation.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.lonelywh1te.introgymapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userData: SharedPreferences
    private lateinit var appSettings: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        appSettings = requireActivity().getSharedPreferences("APP_SETTINGS", MODE_PRIVATE)

        binding.themeSwitcher.isChecked = appSettings.getBoolean("NIGHT_THEME", false)

        binding.themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            appSettings.edit().putBoolean("NIGHT_THEME", isChecked).commit()
        }

        return binding.root
    }


}