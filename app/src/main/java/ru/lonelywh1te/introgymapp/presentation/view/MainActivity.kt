package ru.lonelywh1te.introgymapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        setHeaderBackgroundColor(R.id.main) // TODO: придумать что-то получше

        binding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.main -> {
                    setFragment(mainFragment)
                    setHeaderBackgroundColor(it.itemId)
                }
                R.id.workouts -> {
                    setFragment(workoutFragment)
                    setHeaderBackgroundColor(it.itemId)
                }
                R.id.guide -> {
                    setHeaderBackgroundColor(it.itemId)
                    setFragment(guideFragment)
                    setHeaderBackgroundColor(it.itemId)
                }
                R.id.stats -> {
                    setFragment(statsFragment)
                    setHeaderBackgroundColor(it.itemId)
                }
                R.id.profile -> {
                    setFragment(profileFragment)
                    setHeaderBackgroundColor(it.itemId)
                }
            }

            true
        }

        setContentView(binding.root)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun setHeaderBackgroundColor(id: Int) {
        if (id == R.id.main) binding.headerLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.background_block_color))
        else binding.headerLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.background_color))
    }
}