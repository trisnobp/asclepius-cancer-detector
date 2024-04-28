package com.dicoding.asclepius.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class PredictionHistory(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "imageUri")
    var imageUri: String = "",

    @ColumnInfo(name = "confidenceScore")
    var confidenceScore: String? = null,

    @ColumnInfo(name = "predictionResult")
    var predictionResult: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null,
) : Parcelable