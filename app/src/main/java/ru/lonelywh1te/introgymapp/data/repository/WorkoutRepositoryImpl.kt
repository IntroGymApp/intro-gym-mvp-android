package ru.lonelywh1te.introgymapp.data.repository

import ru.lonelywh1te.introgymapp.data.dao.WorkoutDao
import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

class WorkoutRepositoryImpl(private val workoutDao: WorkoutDao): WorkoutRepository {
    override suspend fun createWorkout(workout: Workout) {
        workoutDao.createWorkout(workout)
    }

    override suspend fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }

    override suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    override suspend fun getAllUserWorkouts(): List<Workout> {
        return workoutDao.getAllUserWorkouts()
    }

    override suspend fun getAllWorkoutsByDate(date: Long): List<Workout> {
        return workoutDao.getAllWorkoutsByDate(date)
    }

    override suspend fun getLastCreatedWorkout(): Workout {
        return workoutDao.getLastCreatedWorkout()
    }

    override suspend fun getWorkoutById(id: Int): Workout {
        return workoutDao.getWorkoutById(id)
    }
}