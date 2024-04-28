package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.PredictionHistory

@Dao
interface PredictionHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(prediction: PredictionHistory)

    @Query("SELECT * from predictionhistory ORDER BY date")
    fun getAllPredictionHistory(): LiveData<List<PredictionHistory>>
}