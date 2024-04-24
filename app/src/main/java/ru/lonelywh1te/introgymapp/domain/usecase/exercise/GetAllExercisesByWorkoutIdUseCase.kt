package ru.lonelywh1te.introgymapp.domain.usecase.exercise

import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class GetAllExercisesByWorkoutIdUseCase(private val exerciseRepository: ExerciseRepository) {
    suspend fun execute(workoutId: Int): List<Exercise> {
        return exerciseRepository.getAllExercisesByWorkoutId(workoutId)
    }
}