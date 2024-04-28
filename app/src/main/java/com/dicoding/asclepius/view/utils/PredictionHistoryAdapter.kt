package com.dicoding.asclepius.view.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.PredictionHistory
import com.dicoding.asclepius.databinding.ItemPredictionHistoryBinding

class PredictionHistoryAdapter : androidx.recyclerview.widget.ListAdapter<PredictionHistory, PredictionHistoryAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPredictionHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val prediction = getItem(position)
        holder.bind(prediction, holder)
    }
    class MyViewHolder(val binding: ItemPredictionHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prediction: PredictionHistory, holder: MyViewHolder){
            Glide.with(itemView)
                .load(Uri.parse(prediction.imageUri))
                .into(binding.imgItemPhoto)

            val cleanedFormat = prediction.confidenceScore?.replace("%", "")?.trim()!!.toFloat() / 100
            cleanedFormat.let {
                binding.predictionScore.text = prediction.confidenceScore
                if (it < 0.5) {
                    binding.predictionScore.setTextColor(ContextCompat.getColor(holder.itemView.context ,R.color.red))
                } else if (it in 0.5..0.75) {
                    binding.predictionScore.setTextColor(ContextCompat.getColor(holder.itemView.context ,R.color.yellow))
                } else {
                    binding.predictionScore.setTextColor(ContextCompat.getColor(holder.itemView.context ,R.color.green))
                }
            }

            binding.predictionDate.text = prediction.date.toString().subSequence(0, 10)

            binding.predictionLabel.text = prediction.predictionResult
            prediction.predictionResult.let {
                if (it.equals("Cancer")) {
                    binding.predictionLabel.setTextColor(ContextCompat.getColor(holder.itemView.context ,R.color.red))
                } else {
                    binding.predictionLabel.setTextColor(ContextCompat.getColor(holder.itemView.context ,R.color.green))
                }
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PredictionHistory>() {
            override fun areItemsTheSame(oldItem: PredictionHistory, newItem: PredictionHistory): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: PredictionHistory, newItem: PredictionHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}