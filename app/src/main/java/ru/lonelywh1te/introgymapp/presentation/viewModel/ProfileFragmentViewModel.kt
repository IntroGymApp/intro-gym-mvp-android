package ru.lonelywh1te.introgymapp.presentation.viewModel

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import ru.lonelywh1te.introgymapp.Constants
import java.time.LocalDate

class ProfileFragmentViewModel(app: Application): AndroidViewModel(app) {
    private val userData = app.getSharedPreferences(Constants.USER_DATA_KEY, Context.MODE_PRIVATE)
    private val appSettings = app.getSharedPreferences(Constants.APP_CONFIG_NAME, Context.MODE_PRIVATE)

    val userName = userData.getString(Constants.USER_NAME_KEY, "Пользователь")
    val userSex = userData.getBoolean(Constants.USER_SEX_KEY, false)
    val userHeight = userData.getInt(Constants.USER_HEIGHT_KEY, 0)
    val userWeight = userData.getInt(Constants.USER_WEIGHT_KEY, 0)
    val userDesiredWeight = userData.getInt(Constants.USER_DESIRED_WEIGHT_KEY, 0)
    private val userGoal = userData.getInt(Constants.USER_GOAL_KEY, 0)
    private val userBirthday = userData.getLong(Constants.USER_BIRTHDAY_KEY, 0)
    private val userActivityLevel = userData.getFloat(Constants.USER_ACTIVITY_LEVEL_KEY, 1.2f)

    var calories = 0
    var proteins = 0
    var fats = 0
    var carbohydrates = 0

    init {
        calculateCPFC()
    }

    fun getTheme(): Boolean {
        return appSettings.getBoolean(Constants.NIGHT_THEME_KEY, false)
    }

    fun setTheme(isNight: Boolean) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        appSettings.edit().putBoolean(Constants.NIGHT_THEME_KEY, isNight).apply()
    }

    fun getBMI(): String {
        val heightInMeters = userHeight / 100f
        val bmi = userWeight.toFloat() / (heightInMeters * heightInMeters)

        return String.format("%.1f", bmi)
    }

    fun getIdealWeight(): String {
        val heightInMeters = userHeight / 100f
        val weight = if (!userSex) {
            ((heightInMeters * heightInMeters * 21.745) + 2.2)
        } else {
            ((heightInMeters * heightInMeters * 21.745) - 2.2)
        }

        return String.format("%.1f", weight)
    }

    fun getUserAge(): Int {
        val currentDate = LocalDate.now().toEpochDay() * 86400000L

        return ((currentDate - userBirthday) / 31536000000L).toInt()
    }

    private fun calculateCPFC() {
        //TODO: подобрать более точные расчёты

        val basalMetabolism = (10f * userWeight) + (6.25f * userHeight) - (5 * getUserAge()) - 161
        val metabolism = basalMetabolism * userActivityLevel

        calories = (metabolism - ((metabolism / 100) * 15)).toInt()

        when (userGoal) {
            Constants.WEIGHT_LOSS_GOAL -> {
                proteins = (((metabolism / 100) * 30) / 4).toInt()
                fats = (((metabolism / 100) * 30) / 9).toInt()
                carbohydrates = (((metabolism / 100) * 40) / 4).toInt()
            }

            Constants.WEIGHT_SUPPORT_GOAL -> {
                proteins = (((metabolism / 100) * 25) / 4).toInt()
                fats = (((metabolism / 100) * 25) / 9).toInt()
                carbohydrates = (((metabolism / 100) * 50) / 4).toInt()
            }

            Constants.WEIGHT_GAIN_GOAL -> {
                proteins = (((metabolism / 100) * 35) / 4).toInt()
                fats = (((metabolism / 100) * 15) / 9).toInt()
                carbohydrates = (((metabolism / 100) * 50) / 4).toInt()
            }
        }

    }
}