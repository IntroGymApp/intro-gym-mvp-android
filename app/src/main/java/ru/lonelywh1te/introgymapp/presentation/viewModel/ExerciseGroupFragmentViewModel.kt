package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseInfoByGroupUseCase

class ExerciseGroupFragmentViewModel(
    private val getAllExerciseInfoByGroupUseCase: GetAllExerciseInfoByGroupUseCase
): ViewModel() {
    val exerciseInfoList = MutableLiveData<List<ExerciseInfo>>()

    fun getAllExerciseInfoByGroup(group: String) {
        viewModelScope.launch {
            exerciseInfoList.postValue(getAllExerciseInfoByGroupUseCase.execute(group))
        }
    }
}