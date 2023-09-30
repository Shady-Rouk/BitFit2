package com.example.bitfit1

import android.app.Application

class ExerciseApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}