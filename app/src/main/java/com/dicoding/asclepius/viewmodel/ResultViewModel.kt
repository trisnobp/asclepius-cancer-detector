package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.PredictionHistory
import com.dicoding.asclepius.data.local.repository.PredictionHistoryRepository

class ResultViewModel(application: Application): ViewModel() {
    private val mPredictionHistoryRepository: PredictionHistoryRepository = PredictionHistoryRepository(application)

    fun insert(prediction: PredictionHistory) {
        mPredictionHistoryRepository.insert(prediction)
    }
}