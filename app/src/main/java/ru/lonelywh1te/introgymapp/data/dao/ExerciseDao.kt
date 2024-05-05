package ru.lonelywh1te.introgymapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import ru.lonelywh1te.introgymapp.domain.model.Exercise
import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

@Dao
interface ExerciseDao {
    @Insert
    suspend fun addExercise(exercise: Exercise)

    @Insert
    suspend fun addExerciseHistory(exerciseHistory: ExerciseHistory)

    @Delete
    suspend fun deleteExerciseHistory(exerciseHistory: ExerciseHistory)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise_info where `group_id`=:group")
    suspend fun getAllExercisesInfoByGroup(group: String): List<ExerciseInfo>

    @Transaction
    @Query("SELECT * FROM exercise WHERE workout_id=:id")
    suspend fun getAllExercisesWithInfoByWorkoutId(id: Int): List<ExerciseWithInfo>


    @Query("SELECT * FROM exercise WHERE workout_id=:id")
    suspend fun getAllExercisesByWorkoutId(id: Int): List<Exercise>

    @Query("SELECT * FROM exercise_history WHERE exercise_id=:id ORDER BY id DESC")
    suspend fun getAllExerciseHistoryById(id: Int): List<ExerciseHistory>

    @Query("SELECT * FROM exercise_group ORDER BY name ASC")
    suspend fun getAllExerciseGroup(): List<ExerciseGroup>
}
