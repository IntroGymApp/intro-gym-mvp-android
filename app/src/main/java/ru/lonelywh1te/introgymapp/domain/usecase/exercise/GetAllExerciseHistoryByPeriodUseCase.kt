package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class GetAllExerciseHistoryByPeriodUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(firstDate: Long, lastDate: Long): List<ExerciseHistory> {
        return exerciseRepository.getAllExerciseHistoryByPeriod(firstDate, lastDate)
    }
}