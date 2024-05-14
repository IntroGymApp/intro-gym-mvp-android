package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.CreateWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.DeleteWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetLastCreatedWorkoutUseCase

class WorkoutFragmentViewModel(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    private val getAllWorkoutUseCase: GetAllWorkoutsUseCase,
    private val addExerciseUseCase: AddExerciseUseCase,
    private val getAllExercisesByWorkoutIdUseCase: GetAllExercisesByWorkoutIdUseCase,
    private val getLastCreatedWorkoutUseCase: GetLastCreatedWorkoutUseCase,
): ViewModel() {
    val workoutList = MutableLiveData<List<Workout>>()
    val operationFinished = MutableLiveData<Boolean>()

    fun getAllWorkouts() {
        viewModelScope.launch {
            workoutList.postValue(getAllWorkoutUseCase.execute())
        }
    }

    fun addWorkoutByDate(workout: Workout, date: Long) {
        viewModelScope.launch {
            createWorkoutUseCase.execute(workout.copy(id = 0, date = date))
            val workoutId = getLastCreatedWorkoutUseCase.execute().id
            val exercises = getAllExercisesByWorkoutIdUseCase.execute(workout.id)
            addExercisesByWorkoutId(workoutId, exercises)

            operationFinished.postValue(true)
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            deleteWorkoutUseCase.execute(workout)
        }
    }

    private suspend fun addExercisesByWorkoutId(workoutId: Int, exercises: List<Exercise>) {
        for (exercise in exercises) {
            val workoutExercise = Exercise(workoutId, exercise.exerciseInfoId, exercise.sets, exercise.reps, exercise.weight, exercise.note)
            addExerciseUseCase.execute(workoutExercise)
        }
    }
}