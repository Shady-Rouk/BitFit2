package com.example.bitfit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var exerciseRecordsRecyclerView: RecyclerView
    private val exerciseRecords = mutableListOf<ExerciseRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exerciseRecordsRecyclerView = findViewById(R.id.entriesRv)
        val recordsAdapter = ExerciseRecordAdapter(exerciseRecords)

        lifecycleScope.launch {
            (application as ExerciseApplication).db.exerciseDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    ExerciseRecord(
                        entity.workoutName,
                        entity.workoutCalories,
                        entity.workoutTime
                    )
                }.also { mappedList ->
                    exerciseRecords.clear()
                    exerciseRecords.addAll(mappedList)
                    recordsAdapter.notifyDataSetChanged()
                }
            }
        }

        exerciseRecordsRecyclerView.adapter = recordsAdapter
        exerciseRecordsRecyclerView.layoutManager = LinearLayoutManager(this)

        val addSessionBtn = findViewById<Button>(R.id.launchRecordBtn)

        addSessionBtn.setOnClickListener {
            // launch the detail activity
            val intent = Intent(this, RecordActivity::class.java)
            this.startActivity(intent)
        }
    }
}