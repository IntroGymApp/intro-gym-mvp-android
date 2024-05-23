package ru.lonelywh1te.introgymapp.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.lonelywh1te.introgymapp.Constants
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.ActivityMainBinding
import ru.lonelywh1te.introgymapp.presentation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFirstLaunch()) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        setTheme()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.mainToolbar)
        navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.mainFragment,
            R.id.workoutFragment,
            R.id.guideFragment,
            R.id.statsFragment,
            R.id.profileFragment
        ))

        binding.bottomMenu.apply {
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController)
                return@setOnItemSelectedListener true
            }

            setOnItemReselectedListener {
                navController.popBackStack(destinationId = it.itemId, inclusive = false)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.exerciseExecuteFragment) {
                binding.bottomMenu.visibility = View.GONE
            } else if (binding.bottomMenu.visibility == View.GONE) {
                binding.bottomMenu.visibility = View.VISIBLE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setTheme() {
        val settings = getSharedPreferences(Constants.APP_CONFIG_NAME, MODE_PRIVATE)
        val nightTheme: Boolean = settings.getBoolean(Constants.NIGHT_THEME_KEY, false)

        if (nightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun isFirstLaunch(): Boolean {
        val settings = getSharedPreferences(Constants.APP_CONFIG_NAME, MODE_PRIVATE)
        return settings.getBoolean(Constants.IS_FIRST_LAUNCH_KEY, true)
    }
}