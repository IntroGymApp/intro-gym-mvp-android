package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class GetAllExerciseGroupUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(): List<ExerciseGroup> {
        return exerciseRepository.getAllExerciseGroup()
    }
}