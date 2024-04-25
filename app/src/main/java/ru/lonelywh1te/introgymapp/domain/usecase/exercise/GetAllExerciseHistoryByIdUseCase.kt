package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class GetAllExerciseHistoryByIdUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(exerciseId: Int): List<ExerciseHistory> {
        return exerciseRepository.getAllExerciseHistoryByExerciseId(exerciseId)
    }
}