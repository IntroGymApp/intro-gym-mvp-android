package ru.lonelywh1te.introgymapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.lonelywh1te.introgymapp.R
import ru.lonelywh1te.introgymapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainFragment = MainFragment()
    private val workoutFragment = WorkoutFragment()
    private val guideFragment = GuideFragment()
    private val statsFragment = StatsFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setFragment(mainFragment)

        binding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.main -> {
                    setFragment(mainFragment)
                }
                R.id.workouts -> {
                    setFragment(workoutFragment)
                }
                R.id.guide -> {
                    setFragment(guideFragment)
                }
                R.id.stats -> {
                    setFragment(statsFragment)
                }
                R.id.profile -> {
                    setFragment(profileFragment)
                }
            }

            true
        }

        binding.ibBackButton.setOnClickListener {
            supportFragmentManager.popBackStack()
        }

        setContentView(binding.root)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }


    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        if (supportFragmentManager.backStackEntryCount > 0) {
            Log.i("MainActivity", "popping backstack");
            supportFragmentManager.popBackStack();
        }

        Log.d("MainActivity", "popping backstack");

        return super.getOnBackInvokedDispatcher()
    }
}