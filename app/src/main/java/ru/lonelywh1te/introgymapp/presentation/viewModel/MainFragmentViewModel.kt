package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.workout.DeleteWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsByDateUseCase

class MainFragmentViewModel(
    private val getAllWorkoutsByDateUseCase: GetAllWorkoutsByDateUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase
): ViewModel() {
    val workoutList = MutableLiveData<List<Workout>>()

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
}