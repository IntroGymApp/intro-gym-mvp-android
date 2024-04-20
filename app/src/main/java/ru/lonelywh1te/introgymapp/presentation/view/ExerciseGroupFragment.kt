package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.ExerciseGroupItemBinding
import ru.lonelywh1te.introgymapp.databinding.FragmentExerciseGroupBinding
import ru.lonelywh1te.introgymapp.domain.ExerciseInfo
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseInfoAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseInfoViewModel

private const val GROUP_ARG = "GROUP"

class ExerciseGroupFragment : Fragment() {
    private lateinit var binding: FragmentExerciseGroupBinding
    private lateinit var recycler: RecyclerView
    private lateinit var exerciseInfoViewModel: ExerciseInfoViewModel

    private val args: ExerciseGroupFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseInfoViewModel = ViewModelProvider(this)[ExerciseInfoViewModel::class.java]
        exerciseInfoViewModel.getAllExerciseInfoByGroup(args.groupId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExerciseGroupBinding.inflate(inflater, container, false)
        val adapter = ExerciseInfoAdapter()

        recycler = binding.rvExerciseInfo
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        exerciseInfoViewModel.exerciseInfoList.observe(viewLifecycleOwner) {
            adapter.exerciseInfoList = it
        }

        return binding.root
    }

    companion object {
        fun newInstance(group: String): ExerciseGroupFragment {
            return ExerciseGroupFragment().apply {
                arguments = Bundle().apply {
                    putString(GROUP_ARG, group)
                }
            }
        }
    }

}