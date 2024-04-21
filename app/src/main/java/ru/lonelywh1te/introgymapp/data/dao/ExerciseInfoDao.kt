package ru.lonelywh1te.introgymapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo

@Dao
interface ExerciseInfoDao {

    @Query("SELECT * FROM exercise_info where `group`=:group")
    suspend fun getAllExercisesInfoByGroup(group: String): List<ExerciseInfo>

    @Insert
    suspend fun addExerciseInfo(exerciseInfo: ExerciseInfo)
}