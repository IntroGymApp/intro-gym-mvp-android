package ru.lonelywh1te.introgymapp.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseWithInfo(
    @Embedded val exercise: Exercise,

    @Relation(
        entity = ExerciseInfo::class,
        parentColumn = "exercise_id",
        entityColumn = "id"
    )
    val exerciseInfo: ExerciseInfo
): Parcelable
