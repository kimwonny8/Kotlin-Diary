package com.example.mydays_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiaryRecord(
    @PrimaryKey(autoGenerate = true) val did:Int,
    val year: Int,
    val month: Int,
    val date: Int,
    val title: String,
    val content: String
)
