package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.PredictionHistory

@Database(entities = [PredictionHistory::class], version = 1)
abstract class PredictionHistoryDatabase: RoomDatabase() {

    abstract fun predictionHistoryDao(): PredictionHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: PredictionHistoryDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): PredictionHistoryDatabase {
            if (INSTANCE == null) {
                synchronized(PredictionHistoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PredictionHistoryDatabase::class.java, "prediction_history_database")
                        .build()
                }
            }
            return INSTANCE as PredictionHistoryDatabase
        }
    }
}