package com.example.bitfit2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val exerciseRecords = mutableListOf<ExerciseRecord>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                }
            }
        }

        val workoutRecordFragment: Fragment = WorkoutRecordFragment(this)
        val workoutSummaryFragment: Fragment = WorkoutSummaryFragment(exerciseRecords)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_workouts -> fragment = workoutRecordFragment
                R.id.nav_summary -> fragment = workoutSummaryFragment
            }
            replaceFragment(fragment)
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_workouts

        val addSessionBtn = findViewById<Button>(R.id.launchRecordBtn)
        addSessionBtn.setOnClickListener {
            // launch the detail activity
            val intent = Intent(this, RecordActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame_layout, newFragment)
        fragmentTransaction.commit()
    }
}