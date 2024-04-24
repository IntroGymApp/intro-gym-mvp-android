package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class GetAllExerciseInfoByGroupUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(group: String): List<ExerciseInfo> {
        return exerciseRepository.getAllExercisesInfoByGroup(group)
    }
}