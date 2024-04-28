package com.dicoding.asclepius.view

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.PredictionHistory
import com.dicoding.asclepius.databinding.ActivityPredictionHistoryBinding
import com.dicoding.asclepius.view.utils.PredictionHistoryAdapter
import com.dicoding.asclepius.viewmodel.PredictionHistoryViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory

class PredictionHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictionHistoryBinding
    private lateinit var predictionHistoryViewModel: PredictionHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(EXTRA_SAVE_PREDICTION) != null) {
            showToast(intent.getStringExtra(EXTRA_SAVE_PREDICTION)!!)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvPrediction.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvPrediction.addItemDecoration(itemDecoration)

        predictionHistoryViewModel = obtainViewModel(this@PredictionHistoryActivity)

        predictionHistoryViewModel.getAllPredictionHistory().observe(this) {
            if (it.isEmpty()) {
                binding.tvDescription.visibility = View.VISIBLE
                binding.tvDescription.text = resources.getString(R.string.no_prediction_history)
                binding.rvPrediction.visibility = View.GONE
            } else {
                val adapter = PredictionHistoryAdapter()
                adapter.submitList(it)
                binding.rvPrediction.adapter = adapter

                binding.tvDescription.visibility = View.GONE
                binding.rvPrediction.visibility = View.VISIBLE
            }
        }

        supportActionBar?.title = resources.getString(R.string.prediction_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.white)))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): PredictionHistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PredictionHistoryViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
      const val EXTRA_SAVE_PREDICTION = "save_prediction"
    }
}