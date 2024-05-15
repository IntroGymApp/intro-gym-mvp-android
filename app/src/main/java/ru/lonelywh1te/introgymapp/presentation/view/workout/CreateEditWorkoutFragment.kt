package ru.lonelywh1te.introgymapp.presentation.view.workout

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.FragmentCreateWorkoutBinding
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.presentation.view.adapter.ExerciseAdapter
import ru.lonelywh1te.introgymapp.presentation.view.adapter.OnExerciseItemClick
import ru.lonelywh1te.introgymapp.presentation.viewModel.CreateEditWorkoutFragmentViewModel

class CreateEditWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentCreateWorkoutBinding
    private lateinit var viewModel: CreateEditWorkoutFragmentViewModel
    private lateinit var recycler: RecyclerView
    private val args: CreateEditWorkoutFragmentArgs by navArgs()

    private var editMode = false
    private var workout: Workout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()

        if (args.workoutId != 0) {
            editMode = true

            viewModel.getWorkoutById(args.workoutId)
            viewModel.getExercisesWithInfoByWorkoutId(args.workoutId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateWorkoutBinding.inflate(layoutInflater, container, false)

        val adapter = ExerciseAdapter(object: OnExerciseItemClick {
            override fun onClick(item: ExerciseWithInfo, itemIndex: Int) {
                val action = CreateEditWorkoutFragmentDirections.toPlanExerciseFragment(item, item.exerciseInfo.name, itemIndex)
                findNavController().navigate(action)
            }
        })

        recycler = binding.rvWorkoutExercises
        recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnAddExercise.setOnClickListener {
            addExercise()
        }

        binding.btnSaveWorkout.setOnClickListener {
            viewModel.exerciseList.value = adapter.getList()

            if (!editMode) {
                createWorkout()
            } else {
                updateWorkout()
            }
        }

        viewModel.workout.observe(viewLifecycleOwner) {
            workout = it
            setWorkoutData(it)
        }

        viewModel.exerciseList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        setFragmentResultListener("ADD_EXERCISE") {_, bundle ->
            bundle.getParcelable<ExerciseWithInfo>("exercise")?.let {
                viewModel.addExerciseToList(it)
            }
        }

        setFragmentResultListener("CONFIG_EXERCISE") {_, bundle ->
            val index = bundle.getInt("exerciseIndex")
            bundle.getParcelable<ExerciseWithInfo>("exercise")?.let {changedExercise ->
                viewModel.changeExerciseAtList(index, changedExercise)
            }
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback((ItemTouchHelper.UP or ItemTouchHelper.DOWN), ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.absoluteAdapterPosition
                val toPosition = target.absoluteAdapterPosition

                adapter.onItemMove(fromPosition, toPosition)
                adapter.notifyItemMoved(fromPosition,toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteExerciseAtList(viewHolder.absoluteAdapterPosition)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftLabel("Удалить")
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.negative_color))
                    .setSwipeLeftLabelColor(MaterialColors.getColor(viewHolder.itemView, R.attr.ig_defaultTextColor))
                    .addSwipeLeftCornerRadius(1, 10f)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler)

        return binding.root
    }

    private fun setWorkoutData(workout: Workout) {
        binding.etWorkoutName.setText(workout.name)
        binding.etWorkoutDescription.setText(workout.description)
    }

    private fun updateWorkout() {
        if (binding.etWorkoutName.text.toString().isEmpty()) {
            binding.etWorkoutName.error = "Введите название"
            return
        }

        val updatedWorkout = workout!!.copy(name = binding.etWorkoutName.text.toString(), description = binding.etWorkoutDescription.text.toString(), exerciseCount = viewModel.getExerciseListSize())
        viewModel.updateWorkout(updatedWorkout)

        viewModel.operationFinished.observe(viewLifecycleOwner) { isUpdated ->
            if (isUpdated) findNavController().popBackStack()
        }
    }

    private fun createWorkout() {
        if (binding.etWorkoutName.text.toString().isEmpty()) {
            binding.etWorkoutName.error = "Введите название"
            return
        }

        val workout = Workout(binding.etWorkoutName.text.toString(), binding.etWorkoutDescription.text.toString(), exerciseCount = viewModel.getExerciseListSize())
        viewModel.createWorkout(workout)

        viewModel.operationFinished.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) findNavController().popBackStack()
        }
    }

    private fun addExercise() {
        val action = CreateEditWorkoutFragmentDirections.toExerciseGuideFragment(true)
        findNavController().navigate(action)
    }
}