package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.data.MainDatabase
import ru.lonelywh1te.introgymapp.domain.model.ExerciseWithInfo

class ExerciseViewModel(app: Application): AndroidViewModel(app) {
    val exerciseWithInfoList = MutableLiveData<List<ExerciseWithInfo>>()

    private val exerciseDao = MainDatabase.getInstance(app).exerciseDao()

    fun getAllExercisesWithInfoByWorkoutId(id: Int) {
        viewModelScope.launch {
            exerciseWithInfoList.postValue(exerciseDao.getAllExercisesWithInfoByWorkoutId(id))
        }
    }
}