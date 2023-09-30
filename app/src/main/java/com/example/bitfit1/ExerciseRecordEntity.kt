package com.example.bitfit1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_records_table")
data class ExerciseRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "workoutName") val workoutName: String?,
    @ColumnInfo(name = "workoutCalories") val workoutCalories: Int?,
    @ColumnInfo(name = "workoutTime") val workoutTime: Double?,
)