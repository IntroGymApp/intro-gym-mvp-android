package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentFinishBinding
import ru.lonelywh1te.introgymapp.presentation.view.MainActivity

class FinishFragment : Fragment() {
    private lateinit var binding: FragmentFinishBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFinishBinding.inflate(inflater, container, false)

        binding.btnFinish.setOnClickListener {
            setFirstLaunch()
            requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }

    private fun setFirstLaunch() {
        val settings = requireActivity().getSharedPreferences(Constants.APP_CONFIG_NAME, MODE_PRIVATE)
        settings.edit().putBoolean(Constants.IS_FIRST_LAUNCH_KEY, false).apply()
    }
}