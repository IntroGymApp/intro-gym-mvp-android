package ru.lonelywh1te.introgymapp.data.repository

import ru.lonelywh1te.introgymapp.data.dao.ExerciseDao
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo
import ru.lonelywh1te.introgymapp.domain.repository.ExerciseRepository

class ExerciseRepositoryImpl(private val exerciseDao: ExerciseDao): ExerciseRepository {
    override suspend fun addExercise(exercise: Exercise) {
        exerciseDao.addExercise(exercise)
    }

    override suspend fun addExerciseHistory(exerciseHistory: ExerciseHistory) {
        exerciseDao.addExerciseHistory(exerciseHistory)
    }

    override suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }

    override suspend fun deleteExerciseHistory(exerciseHistory: ExerciseHistory) {
        exerciseDao.deleteExerciseHistory(exerciseHistory)
    }

    override suspend fun getAllExercisesInfoByGroup(group: String): List<ExerciseInfo> {
        return exerciseDao.getAllExercisesInfoByGroup(group)
    }

    override suspend fun getAllExercisesWithInfoByWorkoutId(id: Int): List<ExerciseWithInfo> {
        return exerciseDao.getAllExercisesWithInfoByWorkoutId(id)
    }

    override suspend fun getAllExercisesByWorkoutId(id: Int): List<Exercise> {
        return exerciseDao.getAllExercisesByWorkoutId(id)
    }

    override suspend fun getAllExerciseHistoryByExerciseId(id: Int): List<ExerciseHistory> {
        return exerciseDao.getAllExerciseHistoryById(id)
    }

    override suspend fun getAllExerciseGroup(): List<ExerciseGroup> {
        return exerciseDao.getAllExerciseGroup()
    }
}