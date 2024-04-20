package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.databinding.FragmentGuideBinding
import ru.lonelywh1te.introgymapp.domain.ExerciseGroup
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseGroupAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseGroupItemClick


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private lateinit var recycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGuideBinding.inflate(inflater, container, false)
        val adapter = ExerciseGroupAdapter(object : OnExerciseGroupItemClick {
            override fun onClick(item: ExerciseGroup) {
                val action = GuideFragmentDirections.toExerciseGroupFragment(item.name, item.groupId)
                findNavController().navigate(action)
            }
        })

        recycler = binding.rvGuides
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        adapter.exerciseGroupList = ExerciseGroup.list

        return binding.root
    }
}