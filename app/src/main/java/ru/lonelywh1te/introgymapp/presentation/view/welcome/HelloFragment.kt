package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentHelloBinding

class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHelloBinding.inflate(inflater, container, false)

        binding.btnStart.setOnClickListener {
            val action = HelloFragmentDirections.toGetNameFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}