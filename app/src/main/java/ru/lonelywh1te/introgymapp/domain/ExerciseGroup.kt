package ru.lonelywh1te.introgymapp.domain

data class ExerciseGroup (
    val name: String,
    val groupId: String,
    val img: String,
    val count: Int = 0,
){
    companion object {
        private val assetsFolder = AssetsPath.EXERCISE_GROUP_IMG

        val list = listOf(
            ExerciseGroup (
                "Грудь",
                "chest",
                "$assetsFolder/chest.png",
                2
            ),
            ExerciseGroup (
                "Ноги",
                "legs",
                "$assetsFolder/legs.png",
            ),
            ExerciseGroup (
                "Плечи",
                "shoulders",
                "$assetsFolder/shoulders.png",
            ),
            ExerciseGroup (
                "Предплечье",
                "forearm",
                "$assetsFolder/forearm.png",
            ),
            ExerciseGroup (
                "Пресс",
                "press",
                "$assetsFolder/press.png",
            ),
            ExerciseGroup (
                "Руки Бицепс",
                "biceps",
                "$assetsFolder/biceps.png",
            ),
            ExerciseGroup (
                "Руки Трицепс",
                "triceps",
                "$assetsFolder/triceps.png",
            ),
            ExerciseGroup (
                "Спина",
                "back",
                "$assetsFolder/back.png",
            ),
        )
    }
}
