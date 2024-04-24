package ru.lonelywh1te.introgymapp.domain.usecase.workout

import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

class GetWorkoutByIdUseCase(private val workoutRepository: WorkoutRepository) {
    suspend fun execute(workoutId: Int): Workout {
        return workoutRepository.getWorkoutById(workoutId)
    }
}