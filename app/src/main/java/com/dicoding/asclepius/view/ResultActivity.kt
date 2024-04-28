package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.PredictionHistory
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.viewmodel.PredictionHistoryViewModel
import com.dicoding.asclepius.viewmodel.ResultViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory
import java.time.LocalDate

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        resultViewModel = obtainViewModel(this@ResultActivity)

        val imageData = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_DATA))
        val resultScore = intent.getStringExtra(
            EXTRA_RESULT_SCORE)
        val resultLabel = intent.getStringExtra(
            EXTRA_RESULT_LABEL)

        binding.resultText.text = resources.getString(R.string.result)
        binding.resultImage.setImageURI(imageData)

        binding.resultScore.text = resultScore
        val cleanedFormat = resultScore?.replace("%", "")?.trim()!!.toFloat() / 100
        cleanedFormat.let {
            if (it < 0.5) {
                binding.resultScore.setTextColor(resources.getColor(R.color.red))
            } else if (it in 0.5..0.75) {
                binding.resultScore.setTextColor(resources.getColor(R.color.yellow))
            } else {
                binding.resultScore.setTextColor(resources.getColor(R.color.green))
            }
        }

        binding.resultLabel.text = resultLabel
        resultLabel.let {
            if (it.equals("Cancer")) {
                binding.resultLabel.setTextColor(resources.getColor(R.color.red))
                binding.resultExplanation.text= resources.getString(R.string.cancer_result_true)
            } else {
                binding.resultLabel.setTextColor(resources.getColor(R.color.green))
                binding.resultExplanation.text= resources.getString(R.string.cancer_result_false)
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.now().toString()
            } else {
                "28/04/2024"
            }

            val newHistory = PredictionHistory(
                imageData.toString(),
                resultScore,
                resultLabel,
                currentTime
            )
            resultViewModel.insert(newHistory)
            val intent = Intent(this, PredictionHistoryActivity::class.java)
            intent.putExtra(PredictionHistoryActivity.EXTRA_SAVE_PREDICTION, resources.getString(R.string.save_prediction))
            startActivity(intent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ResultViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ResultViewModel::class.java)
    }

    companion object {
        const val EXTRA_IMAGE_DATA = "imageData"
        const val EXTRA_RESULT_SCORE = "resultScore"
        const val EXTRA_RESULT_DISPLAY = "resultDisplay"
        const val EXTRA_RESULT_LABEL = "resultLabel"
    }

}