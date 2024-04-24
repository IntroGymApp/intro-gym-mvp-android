package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.data.MainDatabase
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.workout.CreateWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.DeleteWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsByDateUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetLastCreatedWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetWorkoutByIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.UpdateWorkoutUseCase

class WorkoutViewModel(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    private val getAllWorkoutsByDateUseCase: GetAllWorkoutsByDateUseCase,
    private val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    private val getLastCreatedWorkoutUseCase: GetLastCreatedWorkoutUseCase,
): ViewModel() {
    val currentWorkout = MutableLiveData<Workout>()
    val workoutList = MutableLiveData<List<Workout>>()

    suspend fun createWorkout(workout: Workout) {
        createWorkoutUseCase.execute(workout)
    }

    suspend fun addWorkoutDate(workout: Workout, date: Long) {
        val workoutDate = workout.copy(date = date, id = 0)
        createWorkout(workoutDate)
    }

    fun getAllWorkoutsByDate(date: Long) {
        viewModelScope.launch {
            workoutList.postValue(getAllWorkoutsByDateUseCase.execute(date))
        }
    }

    suspend fun updateWorkout(workout: Workout) {
        updateWorkoutUseCase.execute(workout)
    }

    suspend fun getLastCreatedWorkout(): Workout {
        return getLastCreatedWorkoutUseCase.execute()
    }

    fun getWorkoutById(id: Int) {
        viewModelScope.launch {
            currentWorkout.postValue(getWorkoutByIdUseCase.execute(id))
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            deleteWorkoutUseCase.execute(workout)
        }
    }

    fun getAllWorkouts() {
        viewModelScope.launch {
            workoutList.postValue(getAllWorkoutsUseCase.execute())
        }
    }
}