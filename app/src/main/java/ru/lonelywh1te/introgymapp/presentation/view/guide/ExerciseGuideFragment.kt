package ru.lonelywh1te.introgymapp.presentation.view.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.databinding.FragmentExerciseGuideBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseGroupAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseGroupItemClick
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseGuideFragmentViewModel


class ExerciseGuideFragment : Fragment() {
    private lateinit var binding: FragmentExerciseGuideBinding
    private lateinit var viewModel: ExerciseGuideFragmentViewModel
    private lateinit var recycler: RecyclerView
    private val args: ExerciseGuideFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = getViewModel()

        binding = FragmentExerciseGuideBinding.inflate(inflater, container, false)
        val adapter = ExerciseGroupAdapter(object : OnExerciseGroupItemClick {
            override fun onClick(item: ExerciseGroup) {
                val action = ExerciseGuideFragmentDirections.toExerciseGroupFragment(item.name, item.textId, args.pickMode)
                findNavController().navigate(action)
            }
        })

        recycler = binding.rvGuides
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.exerciseGroupList.observe(viewLifecycleOwner) {
            adapter.exerciseGroupList = it
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExerciseGroups()
    }
}