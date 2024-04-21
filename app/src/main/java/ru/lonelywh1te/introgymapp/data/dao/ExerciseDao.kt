package ru.lonelywh1te.introgymapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import ru.lonelywh1te.introgymapp.domain.model.Exercise

@Dao
interface ExerciseDao {
    @Insert
    suspend fun createExercise(exercise: Exercise)
}