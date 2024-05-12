package ru.lonelywh1te.introgymapp.presentation.view.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.lonelywh1te.introgymapp.databinding.FragmentGuideBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath

class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGuideBinding.inflate(inflater, container, false)
        setGuideImages()

        binding.exerciseGuideCard.setOnClickListener {
            val action = GuideFragmentDirections.toExerciseGuideFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun setGuideImages() {
        Glide.with(requireContext())
            .load("${AssetsPath.ASSETS}/guides_img/workout_guide_img.png")
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivExerciseGuide)
    }
}