package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.ExerciseGroup
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseGroupUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseInfoByGroupUseCase

class ExerciseGuideFragmentViewModel(
    private val getAllExerciseGroupUseCase: GetAllExerciseGroupUseCase,
    private val getAllExerciseInfoByGroupUseCase: GetAllExerciseInfoByGroupUseCase
): ViewModel() {
    val exerciseGroupList = MutableLiveData<List<ExerciseGroup>>()

    fun getExerciseGroups() {
        viewModelScope.launch {
            val list = getAllExerciseGroupUseCase.execute()

            for (group in list) {
                val exerciseCount = getAllExerciseInfoByGroupUseCase.execute(group.textId).size
                group.count = exerciseCount
            }

            exerciseGroupList.postValue(list)
        }
    }
}