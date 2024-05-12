package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class DeleteAllExercisesByWorkoutIdUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(workoutId: Int) {
        exerciseRepository.deleteAllExercisesByWorkoutId(workoutId)
    }
}