package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class DeleteExerciseHistoryUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(exerciseHistory: ExerciseHistory) {
        exerciseRepository.deleteExerciseHistory(exerciseHistory)
    }
}