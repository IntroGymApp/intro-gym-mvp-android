package ru.lonelywh1te.introgymapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lonelywh1te.introgymapp.presentation.viewModel.CreateEditWorkoutFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseExecuteFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseGroupFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.ExerciseGuideFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.MainFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.ProfileFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.StatsFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutFragmentViewModel
import ru.lonelywh1te.introgymapp.presentation.viewModel.WorkoutViewFragmentViewModel

val appModule = module {
    viewModel<MainFragmentViewModel> {
        MainFragmentViewModel(
            getAllWorkoutsByDateUseCase = get(),
            deleteWorkoutUseCase = get()
        )
    }

    viewModel<CreateEditWorkoutFragmentViewModel> {
        CreateEditWorkoutFragmentViewModel(
            getWorkoutByIdUseCase = get(),
            getLastCreatedWorkoutUseCase = get(),
            getAllExercisesWithInfoByWorkoutIdUseCase = get(),
            addExerciseUseCase = get(),
            updateWorkoutUseCase = get(),
            createWorkoutUseCase = get(),
            deleteAllExercisesByWorkoutIdUseCase = get()
        )
    }

    viewModel<ExerciseGuideFragmentViewModel> {
        ExerciseGuideFragmentViewModel(
            getAllExerciseGroupUseCase = get(),
            getAllExerciseInfoByGroupUseCase = get()
        )
    }

    viewModel<WorkoutFragmentViewModel> {
        WorkoutFragmentViewModel(
            createWorkoutUseCase = get(),
            addExerciseUseCase = get(),
            deleteWorkoutUseCase = get(),
            getAllExercisesByWorkoutIdUseCase = get(),
            getLastCreatedWorkoutUseCase = get(),
            getAllWorkoutUseCase = get()
        )
    }

    viewModel<WorkoutViewFragmentViewModel> {
        WorkoutViewFragmentViewModel(
            deleteWorkoutUseCase = get(),
            getWorkoutByIdUseCase = get(),
            getAllExercisesWithInfoByWorkoutIdUseCase = get()
        )
    }

    viewModel<ExerciseExecuteFragmentViewModel> {
        ExerciseExecuteFragmentViewModel (
            addHistoryByIdUseCase = get(),
            getAllExerciseHistoryByIdUseCase = get()
        )
    }

    viewModel<ExerciseGroupFragmentViewModel> {
        ExerciseGroupFragmentViewModel(
            getAllExerciseInfoByGroupUseCase = get()
        )
    }

    viewModel<StatsFragmentViewModel> {
        StatsFragmentViewModel(
            getAllExerciseHistoryByPeriodUseCase = get()
        )
    }

    viewModel<ProfileFragmentViewModel> {
        ProfileFragmentViewModel(get())
    }
}