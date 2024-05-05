package ru.lonelywh1te.introgymapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "workout")
data class Workout (
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("description")
    val description: String? = null,
    @ColumnInfo("exercise_count")
    val exerciseCount: Int,
    @ColumnInfo("date")
    val date: Long? = null,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
): Parcelable
