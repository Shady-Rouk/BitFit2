package com.example.bitfit2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class WorkoutRecordFragment(private val activity: MainActivity) : Fragment() {
    private val exerciseRecords = mutableListOf<ExerciseRecord>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_record, container, false)
        val exerciseRecordsRecyclerView = view.findViewById<View>(R.id.workout_list) as RecyclerView
        val recordsAdapter = ExerciseRecordAdapter(exerciseRecords)
        val context = view.context

        lifecycleScope.launch {
            (activity.application as ExerciseApplication).db.exerciseDao().getAll().collect { databaseList ->
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
        exerciseRecordsRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }
}