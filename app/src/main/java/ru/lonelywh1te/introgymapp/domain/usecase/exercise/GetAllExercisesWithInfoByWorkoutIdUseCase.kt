package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class GetAllExercisesWithInfoByWorkoutIdUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(workoutId: Int): List<ExerciseWithInfo> {
        return exerciseRepository.getAllExercisesWithInfoByWorkoutId(workoutId)
    }
}