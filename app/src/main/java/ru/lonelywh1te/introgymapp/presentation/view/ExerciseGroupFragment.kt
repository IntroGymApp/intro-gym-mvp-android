package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentExerciseGroupBinding
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseInfoAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseInfoItemClick
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseInfoViewModel

class ExerciseGroupFragment : Fragment() {
    private lateinit var binding: FragmentExerciseGroupBinding
    private lateinit var recycler: RecyclerView
    private lateinit var exerciseInfoViewModel: ExerciseInfoViewModel

    private val args: ExerciseGroupFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.println(Log.DEBUG, "ExerciseGroupFragment", "${args.pickMode}")
        exerciseInfoViewModel = ViewModelProvider(this)[ExerciseInfoViewModel::class.java]
        exerciseInfoViewModel.getAllExerciseInfoByGroup(args.groupId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExerciseGroupBinding.inflate(inflater, container, false)


        val adapter = ExerciseInfoAdapter(object : OnExerciseInfoItemClick {
            override fun onClick(item: ExerciseInfo) {
                if (args.pickMode) {
                    val bundle = Bundle().apply {
                        putParcelable("exercise", ExerciseWithInfo(Exercise(exerciseInfoId = item.id), item))
                    }

                    setFragmentResult("ADD_EXERCISE", bundle)
                    findNavController().popBackStack(R.id.createEditWorkoutFragment, false)
                } else {
                    val action = ExerciseGroupFragmentDirections.toExerciseInfoFragment(item.name, item)
                    findNavController().navigate(action)
                }
            }
        }, args.pickMode)

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
}