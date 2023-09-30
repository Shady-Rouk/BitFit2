package com.example.bitfit1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseRecordAdapter (private val exerciseRecords: List<ExerciseRecord>)
    : RecyclerView.Adapter<ExerciseRecordAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mWorkoutName: TextView = mView.findViewById<View>(R.id.displayName) as TextView
        val mWorkoutCalories: TextView = mView.findViewById<View>(R.id.displayCalories) as TextView
        val mWorkoutTime: TextView = mView.findViewById<View>(R.id.displayTime) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exerciseRecords.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val record = exerciseRecords[position]

        holder.mWorkoutName.text = record.workoutName
        holder.mWorkoutCalories.text = record.workoutCalories.toString() + " Cal."
        holder.mWorkoutTime.text = record.workoutTime.toString() + " min."
    }
}