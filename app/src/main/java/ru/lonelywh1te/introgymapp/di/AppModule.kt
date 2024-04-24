package ru.lonelywh1te.introgymapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewModel

val appModule = module {
    viewModel<WorkoutViewModel> {
        WorkoutViewModel(
            createWorkoutUseCase = get(),
            deleteWorkoutUseCase = get(),
            updateWorkoutUseCase = get(),
            getAllWorkoutsByDateUseCase = get(),
            getAllWorkoutsUseCase = get(),
            getLastCreatedWorkoutUseCase = get(),
            getWorkoutByIdUseCase = get(),
        )
    }

    viewModel<ExerciseViewModel> {
        ExerciseViewModel(
            addExerciseUseCase = get(),
            updateExerciseUseCase = get(),
            getAllExerciseInfoByGroupUseCase = get(),
            getAllExercisesByWorkoutIdUseCase = get(),
            getAllExercisesWithInfoByWorkoutIdUseCase = get()
        )
    }
}