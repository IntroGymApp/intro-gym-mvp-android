package ru.lonelywh1te.introgymapp.domain

data class ExerciseGroup (
    val name: String,
    val imageAssetPath: String,
    val count: Int,
){
    companion object {
        private const val ASSETS_FOLDER = "exercise_groups_img"

        fun getGroups(): List<ExerciseGroup> {
            return listOf(
                ExerciseGroup (
                    "Грудь",
                    "$ASSETS_FOLDER/chest.png",
                    0
                ),
                ExerciseGroup (
                    "Ноги",
                    "$ASSETS_FOLDER/legs.png",
                    0
                ),
                ExerciseGroup (
                    "Плечи",
                    "$ASSETS_FOLDER/shoulders.png",
                    0
                ),
                ExerciseGroup (
                    "Предплечье",
                    "$ASSETS_FOLDER/forearm.png",
                    0
                ),
                ExerciseGroup (
                    "Пресс",
                    "$ASSETS_FOLDER/press.png",
                    0
                ),
                ExerciseGroup (
                    "Руки Бицепс",
                    "$ASSETS_FOLDER/biceps.png",
                    0
                ),
                ExerciseGroup (
                    "Руки Трицепс",
                    "$ASSETS_FOLDER/triceps.png",
                    0
                ),
                ExerciseGroup (
                    "Спина",
                    "$ASSETS_FOLDER/back.png",
                    0
                ),
            )
        }
    }
}
