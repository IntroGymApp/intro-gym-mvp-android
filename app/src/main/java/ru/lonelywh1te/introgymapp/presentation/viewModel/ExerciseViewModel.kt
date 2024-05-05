package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseHistoryUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.DeleteExerciseHistoryUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseGroupUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseHistoryByIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseInfoByGroupUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesWithInfoByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.UpdateExerciseUseCase

class ExerciseViewModel(
    private val addExerciseUseCase: AddExerciseUseCase,
    private val addExerciseHistoryUseCase: AddExerciseHistoryUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val deleteExerciseHistoryUseCase: DeleteExerciseHistoryUseCase,
    private val getAllExerciseInfoByGroupUseCase: GetAllExerciseInfoByGroupUseCase,
    private val getAllExercisesByWorkoutIdUseCase: GetAllExercisesByWorkoutIdUseCase,
    private val getAllExercisesWithInfoByWorkoutIdUseCase: GetAllExercisesWithInfoByWorkoutIdUseCase,
    private val getAllExerciseHistoryByIdUseCase: GetAllExerciseHistoryByIdUseCase,
    private val getAllExerciseGroupUseCase: GetAllExerciseGroupUseCase
): ViewModel() {
    val exerciseInfoList = MutableLiveData<List<ExerciseInfo>>()
    val exerciseGroupList = MutableLiveData<List<ExerciseGroup>>()
    val exerciseWithInfoList = MutableLiveData<List<ExerciseWithInfo>>()
    val exerciseHistoryList = MutableLiveData<List<ExerciseHistory>>()

    fun getAllExercisesWithInfoByWorkoutId(id: Int) {
        viewModelScope.launch {
            exerciseWithInfoList.postValue(getAllExercisesWithInfoByWorkoutIdUseCase.execute(id))
        }
    }

    fun getAllExerciseInfoByGroup(group: String) {
        viewModelScope.launch {
            exerciseInfoList.postValue(getAllExerciseInfoByGroupUseCase.execute(group))
        }
    }

    fun getAllExerciseGroup() {
        viewModelScope.launch {
            val list = getAllExerciseGroupUseCase.execute()

            for (group in list) {
                val exerciseCount = getAllExerciseInfoByGroupUseCase.execute(group.textId).size
                group.count = exerciseCount

                Log.println(Log.DEBUG, "ExerciseVM", "${group.name} = $exerciseCount")
            }

            exerciseGroupList.postValue(list)
        }
    }

    suspend fun addExercises(workoutId:Int, exercises: List<Exercise>) {
        for (exercise in exercises) {
            val workoutExercise = Exercise(workoutId, exercise.exerciseInfoId, exercise.sets, exercise.reps, exercise.weight, exercise.note)
            addExerciseUseCase.execute(workoutExercise)
        }
    }

    suspend fun updateExercises(workoutId: Int, exercises: List<Exercise>) {
        for (exercise in exercises) {
            if (exercise.id != 0) {
                val workoutExercise = Exercise(workoutId, exercise.exerciseInfoId, exercise.sets, exercise.reps, exercise.weight, exercise.note, exercise.id)
                updateExerciseUseCase.execute(workoutExercise)
            } else {
                val workoutExercise = Exercise(workoutId, exercise.exerciseInfoId, exercise.sets, exercise.reps, exercise.weight, exercise.note)
                addExerciseUseCase.execute(workoutExercise)
            }
        }
    }

    suspend fun getAllExercisesByWorkoutId(id: Int): List<Exercise> {
        return getAllExercisesByWorkoutIdUseCase.execute(id)
    }

    fun addExerciseHistory(exerciseHistory: ExerciseHistory, exerciseId: Int) {
        viewModelScope.launch {
            addExerciseHistoryUseCase.execute(exerciseHistory)
            getAllExerciseHistoryById(exerciseId)
        }
    }

    fun getAllExerciseHistoryById(id: Int) {
        viewModelScope.launch {
            exerciseHistoryList.postValue(getAllExerciseHistoryByIdUseCase.execute(id))
        }
    }
}