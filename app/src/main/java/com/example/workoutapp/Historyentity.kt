package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history-table")
data class Historyentity(
    @PrimaryKey
    val date:String
)
