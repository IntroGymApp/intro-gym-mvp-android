package ru.lonelywh1te.introgymapp.domain.usecase.workout

import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

class GetAllWorkoutsUseCase(private val workoutRepository: WorkoutRepository) {
    suspend fun execute(): List<Workout> {
        return workoutRepository.getAllUserWorkouts()
    }
}