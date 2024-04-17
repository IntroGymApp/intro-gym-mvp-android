package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.FragmentGuideBinding
import ru.lonelywh1te.introgymapp.domain.ExerciseGroup
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseGroupAdapter


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private lateinit var recycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGuideBinding.inflate(inflater, container, false)
        val adapter = ExerciseGroupAdapter()

        recycler = binding.rvGuides
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        adapter.list = ExerciseGroup.getGroups()

        return binding.root
    }
}