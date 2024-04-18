package ru.lonelywh1te.introgymapp.presentation.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.lonelywh1te.introgymapp.databinding.ExerciseInfoItemBinding
import ru.lonelywh1te.introgymapp.domain.AssetsPath
import ru.lonelywh1te.introgymapp.domain.ExerciseInfo
import ru.lonelywh1te.introgymapp.presentation.view.ExerciseInfoActivity

class ExerciseInfoAdapter: RecyclerView.Adapter<ExerciseInfoViewHolder>() {
    var exerciseInfoList = listOf<ExerciseInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseInfoViewHolder {
        val binding = ExerciseInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseInfoList.size


    override fun onBindViewHolder(holder: ExerciseInfoViewHolder, position: Int) {
        val item = exerciseInfoList[position]
        val binding = ExerciseInfoItemBinding.bind(holder.itemView)

        binding.exerciseInfoCard.setOnClickListener {
            val intent = Intent(binding.root.context, ExerciseInfoActivity::class.java)
            intent.putExtra("exerciseInfo", item)
            binding.root.context.startActivity(intent)
        }

        holder.bind(item)
    }
}

class ExerciseInfoViewHolder(private val binding: ExerciseInfoItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExerciseInfo) {
        binding.tvExerciseInfoName.text = item.name


        Glide.with(binding.root)
            .load((Uri.parse("${AssetsPath.PREVIEW_EXERCISE_INFO_IMG}/${item.img}")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivExerciseInfoPreviewImage)
    }
}