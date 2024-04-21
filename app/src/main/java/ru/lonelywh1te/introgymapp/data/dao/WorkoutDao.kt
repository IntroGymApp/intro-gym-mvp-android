package ru.lonelywh1te.introgymapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.lonelywh1te.introgymapp.domain.model.Workout

@Dao
interface WorkoutDao {

    @Insert
    suspend fun createWorkout(workout: Workout)

    @Query("SELECT * FROM workout WHERE date is NULL")
    suspend fun getAllUserWorkouts(): List<Workout>

    @Query("SELECT * FROM workout WHERE id=(SELECT MAX(id) FROM workout)")
    suspend fun getLastCreatedWorkout(): Workout
}