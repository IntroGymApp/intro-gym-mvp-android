package ru.lonelywh1te.introgymapp.domain.usecase.workout

import ru.lonelywh1te.introgymapp.domain.model.Workout
import ru.lonelywh1te.introgymapp.domain.repository.WorkoutRepository

class GetAllWorkoutsByDateUseCase(private val workoutRepository: WorkoutRepository) {
    suspend fun execute(date: Long): List<Workout> {
        return workoutRepository.getAllWorkoutsByDate(date)
    }
}