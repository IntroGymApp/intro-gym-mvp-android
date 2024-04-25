package ru.lonelywh1te.introgymapp.presentation.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentExerciseExecuteBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseHistoryAdapter
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel

class ExerciseExecuteFragment : Fragment() {
    private lateinit var binding: FragmentExerciseExecuteBinding
    private lateinit var exerciseWithInfo: ExerciseWithInfo
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var recycler: RecyclerView
    private val args: ExerciseExecuteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseWithInfo = args.exerciseWithInfo
        exerciseViewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExerciseExecuteBinding.inflate(layoutInflater)

        val adapter = ExerciseHistoryAdapter()
        recycler = binding.rvExerciseHistory

        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        setExerciseData()


        binding.ibAddExerciseHistory.setOnClickListener {
            addExerciseSet()
        }

        exerciseViewModel.exerciseHistoryList.observe(viewLifecycleOwner) {
            adapter.exerciseHistory = it.toMutableList()
            Log.println(Log.DEBUG, "ExerciseExecuteFragment", "${exerciseWithInfo.exercise.id} | $it")
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        exerciseViewModel.getAllExerciseHistoryById(exerciseWithInfo.exercise.id)
    }

    private fun setExerciseData() {
        if (!exerciseWithInfo.exercise.note.isNullOrEmpty()) {
            binding.tvExerciseNote.text = exerciseWithInfo.exercise.note
            binding.exerciseDescriptionLayout.visibility = View.VISIBLE
        } else {
            binding.exerciseDescriptionLayout.visibility = View.GONE
        }

        binding.tvExerciseSets.text = "0 / " + exerciseWithInfo.exercise.sets.toString()
        binding.tvExerciseReps.text = exerciseWithInfo.exercise.reps.toString()
        binding.tvExerciseWeight.text = exerciseWithInfo.exercise.weight.toString()

        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.EXERCISE_INFO_IMG}/${exerciseWithInfo.exerciseInfo.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoImage)
    }

    private fun addExerciseSet() {
        val exerciseId = exerciseWithInfo.exercise.id
        val reps = binding.etCompletedReps.text.toString().toInt()
        val weight = binding.etCompletedWeight.text.toString().toInt()
        val data = 0L

        val exerciseHistory = ExerciseHistory(exerciseId, reps, weight, data)

        exerciseViewModel.addExerciseHistory(exerciseHistory, exerciseId)
    }
}