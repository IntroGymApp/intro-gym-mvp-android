package ru.lonelywh1te.introgymapp.di

import org.koin.dsl.module
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseHistoryUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.DeleteAllExercisesByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.DeleteExerciseHistoryUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseGroupUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseHistoryByIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseHistoryByPeriodUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseInfoByGroupUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExercisesWithInfoByWorkoutIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.UpdateExerciseUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.CreateWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.DeleteWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsByDateUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetAllWorkoutsUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetLastCreatedWorkoutUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.GetWorkoutByIdUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.workout.UpdateWorkoutUseCase

val domainModule = module {
    factory<AddExerciseUseCase> { AddExerciseUseCase(get()) }
    factory<AddExerciseHistoryUseCase> { AddExerciseHistoryUseCase(get()) }
    factory<UpdateExerciseUseCase> { UpdateExerciseUseCase(get()) }
    factory<DeleteExerciseHistoryUseCase> { DeleteExerciseHistoryUseCase(get()) }
    factory<GetAllExerciseInfoByGroupUseCase> { GetAllExerciseInfoByGroupUseCase(get()) }
    factory<GetAllExercisesByWorkoutIdUseCase> { GetAllExercisesByWorkoutIdUseCase(get()) }
    factory<GetAllExercisesWithInfoByWorkoutIdUseCase> { GetAllExercisesWithInfoByWorkoutIdUseCase(get()) }
    factory<GetAllExerciseHistoryByIdUseCase> { GetAllExerciseHistoryByIdUseCase(get()) }
    factory<GetAllExerciseGroupUseCase> { GetAllExerciseGroupUseCase(get()) }
    factory<DeleteAllExercisesByWorkoutIdUseCase> { DeleteAllExercisesByWorkoutIdUseCase(get()) }
    factory<GetAllExerciseHistoryByPeriodUseCase> { GetAllExerciseHistoryByPeriodUseCase(get()) }

    factory<CreateWorkoutUseCase> { CreateWorkoutUseCase(get()) }
    factory<DeleteWorkoutUseCase> { DeleteWorkoutUseCase(get()) }
    factory<GetAllWorkoutsByDateUseCase> { GetAllWorkoutsByDateUseCase(get()) }
    factory<GetLastCreatedWorkoutUseCase> { GetLastCreatedWorkoutUseCase(get()) }
    factory<GetWorkoutByIdUseCase> { GetWorkoutByIdUseCase((get())) }
    factory<GetAllWorkoutsUseCase> { GetAllWorkoutsUseCase(get()) }
    factory<UpdateWorkoutUseCase> { UpdateWorkoutUseCase(get()) }
}