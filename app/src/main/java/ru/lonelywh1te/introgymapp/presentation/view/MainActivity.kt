package ru.lonelywh1te.introgymapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.mainFragment,
            R.id.workoutFragment,
            R.id.guideFragment,
            R.id.statsFragment,
            R.id.profileFragment
        ))

        binding.bottomMenu.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }

        // binding.bottomMenu.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}