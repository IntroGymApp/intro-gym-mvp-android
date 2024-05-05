package ru.lonelywh1te.introgymapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("exercise_group")
data class ExerciseGroup (
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("text_id")
    val textId: String,
    @ColumnInfo("img")
    val img: String,
    @ColumnInfo("exercise_count")
    var count: Int = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
