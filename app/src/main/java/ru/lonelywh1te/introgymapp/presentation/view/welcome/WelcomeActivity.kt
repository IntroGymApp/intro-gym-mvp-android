package ru.lonelywh1te.introgymapp.presentation.view.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.lonelywh1te.introgymapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}