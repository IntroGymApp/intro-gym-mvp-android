package ru.lonelywh1te.introgymapp.domain.usecase.workout

import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

class GetLastCreatedWorkoutUseCase(private val workoutRepository: WorkoutRepository) {
    suspend fun execute(): Workout {
        return workoutRepository.getLastCreatedWorkout()
    }
}