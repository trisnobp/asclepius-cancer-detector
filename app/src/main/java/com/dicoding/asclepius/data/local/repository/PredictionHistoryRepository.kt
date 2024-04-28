package com.dicoding.asclepius.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.entity.PredictionHistory
import com.dicoding.asclepius.data.local.room.PredictionHistoryDao
import com.dicoding.asclepius.data.local.room.PredictionHistoryDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PredictionHistoryRepository(application: Application) {
    private val mPredictionDao: PredictionHistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PredictionHistoryDatabase.getDatabase(application)
        mPredictionDao = db.predictionHistoryDao()
    }

    fun insert(prediction: PredictionHistory) {
        executorService.execute {
            mPredictionDao.insert(prediction)
        }
    }

    fun getAllPredictionHistory(): LiveData<List<PredictionHistory>> = mPredictionDao.getAllPredictionHistory()
}