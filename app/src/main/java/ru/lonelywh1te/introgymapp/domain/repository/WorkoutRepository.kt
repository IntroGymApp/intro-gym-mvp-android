package ru.lonelywh1te.introgymapp.domain.repository

import ru.lonelywh1te.introgymapp.domain.model.Workout

interface WorkoutRepository {
    suspend fun createWorkout(workout: Workout)
    suspend fun updateWorkout(workout: Workout)
    suspend fun deleteWorkout(workout: Workout)
    suspend fun getAllUserWorkouts(): List<Workout>
    suspend fun getAllWorkoutsByDate(date: Long): List<Workout>
    suspend fun getLastCreatedWorkout(): Workout
    suspend fun getWorkoutById(id: Int): Workout
}