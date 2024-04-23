package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.data.MainDatabase
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.Workout

class WorkoutViewModel(app: Application): AndroidViewModel(app) {
    val currentWorkout = MutableLiveData<Workout>()
    val workoutList = MutableLiveData<List<Workout>>()

    private val workoutDao = MainDatabase.getInstance(app).workoutDao()
    private val exerciseDao = MainDatabase.getInstance(app).exerciseDao()

    suspend fun createWorkout(workout: Workout, exercises: List<Exercise>) {
        workoutDao.createWorkout(workout)

        val workoutId = workoutDao.getLastCreatedWorkout().id

        for (exercise in exercises) {
            val workoutExercise = exercise.copy(workoutId = workoutId)
            exerciseDao.addExercise(workoutExercise)
        }
    }

    suspend fun updateWorkout(workout: Workout, exercises: List<Exercise>) {
        workoutDao.updateWorkout(workout)

        for (exercise in exercises) {
            val workoutExercise = exercise.copy(workoutId = workout.id)
            exerciseDao.addExercise(workoutExercise)
        }
    }

    fun getWorkoutById(id: Int) {
        viewModelScope.launch {
            currentWorkout.postValue(workoutDao.getWorkoutById(id))
        }
    }

    fun getAllWorkouts() {
        viewModelScope.launch {
            workoutList.postValue(workoutDao.getAllUserWorkouts())
        }
    }
}