package ru.lonelywh1te.introgymapp.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_info")
data class ExerciseInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("group")
    val group: String,
    @ColumnInfo("image_asset_path")
    val imageAssetPath: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("preparation")
    val preparation: String,
    @ColumnInfo("execution")
    val execution: String,
    @ColumnInfo("advices")
    val advices: String,
)