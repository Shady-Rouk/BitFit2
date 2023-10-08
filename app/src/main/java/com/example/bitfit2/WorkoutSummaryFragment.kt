package com.example.bitfit2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class WorkoutSummaryFragment(private val exerciseRecords: List<ExerciseRecord>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_summary, container, false)

        if (exerciseRecords.isNotEmpty()) {
            Log.v("Workout Summary/", "The list of records is not empty")
            updateView(view, exerciseRecords)
        } else {
            Log.e("Workout Summary/", "The list of records is empty")
        }
        return view
    }

    private fun updateView(view: View, exerciseRecords: List<ExerciseRecord>) {
        val avgCaloriesView = view.findViewById(R.id.avgCalories) as TextView
        val avgTimeView = view.findViewById(R.id.avgTime) as TextView
        val totalWorkoutsView = view.findViewById(R.id.totalWorkouts) as TextView
        val totalCaloriesView = view.findViewById(R.id.totalCalories) as TextView
        val totalTimeView = view.findViewById(R.id.totalTime) as TextView

        var totalRecords = exerciseRecords.size
        var totalCalories = 0
        var totalTime = 0.0

        exerciseRecords.forEach { record ->
            if (record.workoutCalories != null && record.workoutTime != null) {
                totalCalories += record.workoutCalories
                totalTime += record.workoutTime
            }
        }

        var avgCalories = BigDecimal(totalCalories / totalRecords).setScale(2, RoundingMode.HALF_UP).toDouble()
        var avgTime = BigDecimal(totalTime / totalRecords).setScale(2, RoundingMode.HALF_UP).toDouble()

        avgCaloriesView.text = "$avgCalories Cal."
        avgTimeView.text = "$avgTime mins."
        if (totalRecords == 1) {
            totalWorkoutsView.text = "$totalRecords workout"
        } else {
            totalWorkoutsView.text = "$totalRecords workouts"
        }
        totalCaloriesView.text = "$totalCalories Cal."
        totalTimeView.text = "$totalTime mins."
    }

}