package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.DeleteExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesWithInfoByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.UpdateExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.CreateWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetLastCreatedWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetWorkoutByIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.UpdateWorkoutUseCase

class CreateEditWorkoutFragmentViewModel(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    private val getLastCreatedWorkoutUseCase: GetLastCreatedWorkoutUseCase,
    private val getAllExercisesWithInfoByWorkoutIdUseCase: GetAllExercisesWithInfoByWorkoutIdUseCase,
    private val addExerciseUseCase: AddExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
): ViewModel() {
    val workout = MutableLiveData<Workout>()
    private val exerciseList = MutableLiveData<List<ExerciseWithInfo>>()
    private val deletedExercises = mutableListOf<ExerciseWithInfo>()
    val newExerciseList = MutableLiveData<MutableList<ExerciseWithInfo>>()

    val operationFinished = MutableLiveData<Boolean>()

    init {
        exerciseList.value = emptyList()
        newExerciseList.value = mutableListOf()
    }

    fun createWorkout(workout: Workout) {
        val exercises = newExerciseList.value!!.map { it.exercise }

        viewModelScope.launch {
            createWorkoutUseCase.execute(workout)

            val workoutId = getLastCreatedWorkoutUseCase.execute().id
            addExercisesByWorkoutId(workoutId, exercises)

            operationFinished.postValue(true)
        }
    }

    fun updateWorkout(workout: Workout) {
        // TODO: неэффективный метод
        viewModelScope.launch {
            updateWorkoutUseCase.execute(workout)

            updateWorkoutExercises(
                exerciseList.value!!.map { it.exercise },
                newExerciseList.value!!.map { it.exercise },
                deletedExercises.map { it.exercise })

            operationFinished.postValue(true)
        }
    }

    private suspend fun updateWorkoutExercises(oldExercises: List<Exercise>, newExercises: List<Exercise>, deletedExercises: List<Exercise>) {
        if (oldExercises == newExercises) return

        if (deletedExercises.isNotEmpty()) {
            for (exercise in deletedExercises) {
                deleteExerciseUseCase.execute(exercise)
            }
        }

        for (i in newExercises.indices) {
            val exercise = oldExercises.find { it.id == newExercises[i].id }

            if (exercise == null) {
                addExerciseUseCase.execute(newExercises[i].copy(workoutId = workout.value!!.id, index = i))
            } else {
                updateExerciseUseCase.execute(newExercises[i].copy(index = i))
            }
        }
    }

    fun getWorkoutById(id: Int) {
        viewModelScope.launch {
            workout.postValue(getWorkoutByIdUseCase.execute(id))
        }
    }

    fun getExercisesWithInfoByWorkoutId(id: Int) {
        viewModelScope.launch {
            exerciseList.postValue(getAllExercisesWithInfoByWorkoutIdUseCase.execute(id))
            newExerciseList.postValue(getAllExercisesWithInfoByWorkoutIdUseCase.execute(id).toMutableList())
        }
    }

    fun addExerciseToList(exerciseWithInfo: ExerciseWithInfo) {
        val newList = newExerciseList.value!!.toMutableList()
        newList.add(exerciseWithInfo)
        newExerciseList.value = newList
    }

    fun deleteExerciseFromList(index: Int) {
        val deletedExercise = newExerciseList.value!!.toMutableList()[index]
        deletedExercises.add(deletedExercise)
        newExerciseList.value!!.removeAt(index)
    }

    fun changeExerciseAtList(index: Int, exerciseWithInfo: ExerciseWithInfo){
        val newList = newExerciseList.value!!.toMutableList()
        newList[index] = exerciseWithInfo
        newExerciseList.value = newList
    }

    fun moveExercise(fromPosition: Int, toPosition: Int) {
        val fromItem = newExerciseList.value!![fromPosition]

        newExerciseList.value!!.removeAt(fromPosition)
        newExerciseList.value!!.add(toPosition, fromItem)
    }

    fun getExerciseListSize(): Int = newExerciseList.value!!.size

    private suspend fun addExercisesByWorkoutId(workoutId: Int, exercises: List<Exercise>) {
        for (i in exercises.indices) {
            addExerciseUseCase.execute(exercises[i].copy(workoutId = workoutId, index = i))
        }
    }
}