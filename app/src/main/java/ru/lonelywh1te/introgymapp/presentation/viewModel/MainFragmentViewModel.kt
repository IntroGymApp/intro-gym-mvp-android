package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.workout.DeleteWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsByDateUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsUseCase

class MainFragmentViewModel(
    private val getAllWorkoutsByDateUseCase: GetAllWorkoutsByDateUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    private val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
): ViewModel() {
    val workoutList = MutableLiveData<List<Workout>>()
    var allWorkoutsCount = 0

    init {
        countAllWorkouts()
    }

    fun getWorkoutsByDate(date: Long){
        viewModelScope.launch {
            workoutList.postValue(getAllWorkoutsByDateUseCase.execute(date))
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            deleteWorkoutUseCase.execute(workout)
        }
    }

    fun countAllWorkouts() {
        // TODO: возвращать подсчёт из бд
        viewModelScope.launch {
            val workouts = getAllWorkoutsUseCase.execute()
            allWorkoutsCount = workouts.size
        }
    }
}