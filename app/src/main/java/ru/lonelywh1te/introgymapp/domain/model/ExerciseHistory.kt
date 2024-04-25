package ru.lonelywh1te.introgymapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_history", foreignKeys = [
    ForeignKey(
        entity = Exercise::class,
        parentColumns = ["id"],
        childColumns = ["exercise_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class ExerciseHistory(
    @ColumnInfo("exercise_id")
    val exerciseId: Int,
    @ColumnInfo("reps")
    val reps: Int,
    @ColumnInfo("weight")
    val weight: Int,
    @ColumnInfo("date")
    val date: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)