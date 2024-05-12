package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesWithInfoByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.DeleteWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetWorkoutByIdUseCase

class WorkoutViewFragmentViewModel(
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    private val getAllExercisesWithInfoByWorkoutIdUseCase: GetAllExercisesWithInfoByWorkoutIdUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
): ViewModel() {
    val workout = MutableLiveData<Workout>()
    val exerciseList = MutableLiveData<List<ExerciseWithInfo>>()

    fun getWorkoutById(id: Int) {
        viewModelScope.launch {
            workout.postValue(getWorkoutByIdUseCase.execute(id))
        }
    }

    fun getExercisesWithInfoByWorkoutId(id: Int) {
        viewModelScope.launch {
            exerciseList.postValue(getAllExercisesWithInfoByWorkoutIdUseCase.execute(id))
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            deleteWorkoutUseCase.execute(workout)
        }
    }
}