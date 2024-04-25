package ru.lonelywh1te.introgymapp.domain.repository

import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

interface ExerciseRepository {
    suspend fun addExercise(exercise: Exercise)
    suspend fun addExerciseHistory(exerciseHistory: ExerciseHistory)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExerciseHistory(exerciseHistory: ExerciseHistory)
    suspend fun getAllExercisesInfoByGroup(group: String): List<ExerciseInfo>
    suspend fun getAllExercisesWithInfoByWorkoutId(id: Int): List<ExerciseWithInfo>
    suspend fun getAllExercisesByWorkoutId(id: Int): List<Exercise>
    suspend fun getAllExerciseHistoryByExerciseId(id: Int): List<ExerciseHistory>
}