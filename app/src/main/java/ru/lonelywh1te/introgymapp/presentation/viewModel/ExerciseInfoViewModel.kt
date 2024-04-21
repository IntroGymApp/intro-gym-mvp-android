package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lonelywh1te.introgymapp.data.MainDatabase
import ru.lonelywh1te.introgymapp.domain.model.ExerciseInfo

class ExerciseInfoViewModel(app: Application): AndroidViewModel(app) {
    val exerciseInfoList = MutableLiveData<List<ExerciseInfo>>()

    private val exerciseInfoDao = MainDatabase.getInstance(app).exerciseInfoDao()

    fun getAllExerciseInfoByGroup(group: String) {
        viewModelScope.launch {
            exerciseInfoList.postValue(exerciseInfoDao.getAllExercisesInfoByGroup(group))
        }
    }

}