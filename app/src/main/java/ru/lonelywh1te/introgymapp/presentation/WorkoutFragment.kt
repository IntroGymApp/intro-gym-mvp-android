package ru.lonelywh1te.introgymapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.lonelywh1te.introgymapp.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }
}