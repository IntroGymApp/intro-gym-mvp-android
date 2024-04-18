package ru.lonelywh1te.introgymapp.domain

data class ExerciseGroup (
    val name: String,
    val groupId: String,
    val img: String,
    val count: Int,
){
    companion object {
        private const val ASSETS_FOLDER = AssetsPath.EXERCISE_GROUP_IMG

        fun getGroups(): List<ExerciseGroup> {
            return listOf(
                ExerciseGroup (
                    "Грудь",
                    "chest",
                    "$ASSETS_FOLDER/chest.png",
                    0
                ),
                ExerciseGroup (
                    "Ноги",
                    "legs",
                    "$ASSETS_FOLDER/legs.png",
                    0
                ),
                ExerciseGroup (
                    "Плечи",
                    "shoulders",
                    "$ASSETS_FOLDER/shoulders.png",
                    0
                ),
                ExerciseGroup (
                    "Предплечье",
                    "forearm",
                    "$ASSETS_FOLDER/forearm.png",
                    0
                ),
                ExerciseGroup (
                    "Пресс",
                    "press",
                    "$ASSETS_FOLDER/press.png",
                    0
                ),
                ExerciseGroup (
                    "Руки Бицепс",
                    "biceps",
                    "$ASSETS_FOLDER/biceps.png",
                    0
                ),
                ExerciseGroup (
                    "Руки Трицепс",
                    "triceps",
                    "$ASSETS_FOLDER/triceps.png",
                    0
                ),
                ExerciseGroup (
                    "Спина",
                    "back",
                    "$ASSETS_FOLDER/back.png",
                    0
                ),
            )
        }
    }
}
