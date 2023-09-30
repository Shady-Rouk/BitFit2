package com.example.bitfit1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise_records_table")
    fun getAll(): Flow<List<ExerciseRecordEntity>>


    @Insert
    fun insert(exerciseRecord: ExerciseRecordEntity)
}