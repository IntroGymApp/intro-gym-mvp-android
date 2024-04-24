package ru.lonelywh1te.introgymapp.di

import org.koin.dsl.module
import ru.lonelywh1te.introgymapp.data.MainDatabase
import ru.lonelywh1te.introgymapp.data.dao.ExerciseDao
import ru.lonelywh1te.introgymapp.data.dao.WorkoutDao
import ru.lonelywh1te.introgymapp.data.repository.ExerciseRepositoryImpl
import ru.lonelywh1te.introgymapp.data.repository.WorkoutRepositoryImpl
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

val dataModule = module {
    single<MainDatabase> {
        MainDatabase.getInstance(get())
    }

    single<WorkoutDao> {
        get<MainDatabase>().workoutDao()
    }

    single<ExerciseDao> {
        get<MainDatabase>().exerciseDao()
    }

    single<WorkoutRepository> {
        WorkoutRepositoryImpl(get())
    }

    single<ExerciseRepository> {
        ExerciseRepositoryImpl(get())
    }
}