package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.PredictionHistory
import com.dicoding.asclepius.data.local.repository.PredictionHistoryRepository

class PredictionHistoryViewModel(application: Application): ViewModel() {

    private val mPredictionHistoryRepository: PredictionHistoryRepository = PredictionHistoryRepository(application)

    fun getAllPredictionHistory(): LiveData<List<PredictionHistory>> {
        return mPredictionHistoryRepository.getAllPredictionHistory()
    }
}