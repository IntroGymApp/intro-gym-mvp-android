package ru.lonelywh1te.introgymapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.lonelywh1te.introgymapp.domain.model.Workout

@Dao
interface WorkoutDao {

    @Insert
    suspend fun createWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout WHERE date is NULL")
    suspend fun getAllUserWorkouts(): List<Workout>

    @Query("SELECT * FROM workout WHERE date=:date")
    suspend fun getAllWorkoutsByDate(date: Long): List<Workout>

    @Query("SELECT * FROM workout WHERE id=(SELECT MAX(id) FROM workout)")
    suspend fun getLastCreatedWorkout(): Workout

    @Query("SELECT * FROM workout WHERE id=:id")
    suspend fun getWorkoutById(id: Int): Workout
}