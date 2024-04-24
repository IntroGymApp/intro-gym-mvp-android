package ru.lonelywh1te.introgymapp.domain.usecase.workout

import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

class CreateWorkoutUseCase(private val workoutRepository: WorkoutRepository) {
    suspend fun execute(workout: Workout) {
        workoutRepository.createWorkout(workout)
    }
}