package ru.lonelywh1te.introgymapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExercise(exercise: Exercise)

    @Transaction
    @Query("SELECT * FROM exercise WHERE workout_id=:id")
    suspend fun getAllExercisesWithInfoByWorkoutId(id: Int): List<ExerciseWithInfo>
}