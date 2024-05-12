package ru.lonelywh1te.introgymapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.domain.model.ExerciseHistory
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.AddExerciseHistoryUseCase
import ru.lonelywh1te.introgymapp.domain.usecase.exercise.GetAllExerciseHistoryByIdUseCase

class ExerciseExecuteFragmentViewModel(
    private val getAllExerciseHistoryByIdUseCase: GetAllExerciseHistoryByIdUseCase,
    private val addHistoryByIdUseCase: AddExerciseHistoryUseCase,
): ViewModel() {
    val exerciseHistory = MutableLiveData<List<ExerciseHistory>>()

    fun addExerciseHistory(history: ExerciseHistory) {
        viewModelScope.launch {
            addHistoryByIdUseCase.execute(history)
            exerciseHistory.postValue(getAllExerciseHistoryByIdUseCase.execute(history.exerciseId))
        }
    }

    fun getExerciseHistoryById(id: Int){
        viewModelScope.launch {
            exerciseHistory.postValue(getAllExerciseHistoryByIdUseCase.execute(id))
        }
    }
}