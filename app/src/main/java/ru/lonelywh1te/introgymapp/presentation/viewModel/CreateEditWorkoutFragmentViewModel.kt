package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.DeleteAllExercisesByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesWithInfoByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.UpdateExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.CreateWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetLastCreatedWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetWorkoutByIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.UpdateWorkoutUseCase

class CreateEditWorkoutFragmentViewModel(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    private val getLastCreatedWorkoutUseCase: GetLastCreatedWorkoutUseCase,
    private val getAllExercisesWithInfoByWorkoutIdUseCase: GetAllExercisesWithInfoByWorkoutIdUseCase,
    private val addExerciseUseCase: AddExerciseUseCase,
    private val deleteAllExercisesByWorkoutIdUseCase: DeleteAllExercisesByWorkoutIdUseCase
): ViewModel() {
    val workout = MutableLiveData<Workout>()
    val exerciseList = MutableLiveData<List<ExerciseWithInfo>>()
    val operationFinished = MutableLiveData<Boolean>()

    fun createWorkout(workout: Workout, exercises: List<Exercise>) {
        viewModelScope.launch {
            createWorkoutUseCase.execute(workout)

            val workoutId = getLastCreatedWorkoutUseCase.execute().id
            addExercisesByWorkoutId(workoutId, exercises)

            operationFinished.postValue(true)
        }
    }

    fun updateWorkout(workout: Workout, exercises: List<Exercise>) {
        // TODO: неэффективный метод
        viewModelScope.launch {
            updateWorkoutUseCase.execute(workout)
            deleteAllExercisesByWorkoutId(workout.id)
            addExercisesByWorkoutId(workout.id, exercises)

            operationFinished.postValue(true)
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
        }
    }


    private suspend fun deleteAllExercisesByWorkoutId(id: Int) {
        deleteAllExercisesByWorkoutIdUseCase.execute(id)
    }

    private suspend fun addExercisesByWorkoutId(workoutId: Int, exercises: List<Exercise>) {
        for (exercise in exercises) {
            val workoutExercise = Exercise(workoutId, exercise.exerciseInfoId, exercise.sets, exercise.reps, exercise.weight, exercise.note)
            addExerciseUseCase.execute(workoutExercise)
        }
    }
}