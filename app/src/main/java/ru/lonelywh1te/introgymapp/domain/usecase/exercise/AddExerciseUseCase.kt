package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class AddExerciseUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(exercise: Exercise) {
        exerciseRepository.addExercise(exercise)
    }
}