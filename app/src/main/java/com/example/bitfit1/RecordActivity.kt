package com.example.bitfit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RecordActivity : AppCompatActivity() {
    private lateinit var workoutNameInputView: EditText
    private lateinit var workoutCaloriesInputView: EditText
    private lateinit var workoutTimeInputView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        workoutNameInputView = findViewById(R.id.inputName)
        workoutCaloriesInputView = findViewById(R.id.inputCalories)
        workoutTimeInputView = findViewById(R.id.inputTime)

        val saveRecordBtn = findViewById<Button>(R.id.saveWorkoutBtn)
        var exerciseRecord: ExerciseRecord? = null

        saveRecordBtn.setOnClickListener {
            exerciseRecord = ExerciseRecord(
                workoutNameInputView.text.toString(),
                workoutCaloriesInputView.text.toString().toInt(),
                workoutTimeInputView.text.toString().toDouble())

            exerciseRecord?.let { obj ->
                lifecycleScope.launch(IO) {
                    (application as ExerciseApplication).db.exerciseDao().insert(
                        ExerciseRecordEntity(
                            workoutName = obj.workoutName,
                            workoutCalories = obj.workoutCalories,
                            workoutTime = obj.workoutTime
                        )
                    )
                }
            }

            // launch main activity
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

    }
}