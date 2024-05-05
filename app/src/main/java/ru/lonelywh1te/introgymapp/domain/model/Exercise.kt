package ru.lonelywh1te.introgymapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "exercise", foreignKeys = [
    ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workout_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = ExerciseInfo::class,
        parentColumns = ["id"],
        childColumns = ["exercise_info_id"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class Exercise(
    @ColumnInfo("workout_id")
    val workoutId: Int = 0,
    @ColumnInfo("exercise_info_id")
    val exerciseInfoId: Int = 0,
    @ColumnInfo("sets")
    val sets: Int = 0,
    @ColumnInfo("reps")
    val reps: Int = 0,
    @ColumnInfo("weight")
    val weight: Float = 0f,
    @ColumnInfo("note")
    val note: String? = null,

    @PrimaryKey(autoGenerate = true) val id: Int = 0
): Parcelable
