package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class AddExerciseHistoryUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(exerciseHistory: ExerciseHistory) {
        exerciseRepository.addExerciseHistory(exerciseHistory)
    }
}