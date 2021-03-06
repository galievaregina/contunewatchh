package com.example.contunewatchh

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

class Coroutines : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = "state"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(TIME_SCORE, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        lifecycleScope.launchWhenResumed {
            Log.d(TAG,"Thread launched")
            while (true) {
                delay(1000)
                textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
            }
        }
        Log.d(TAG,"Activity created")
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        Log.d(TAG,"Activity stopped")
        super.onStop()
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(TIME_SCORE, secondsElapsed)
        super.onStart()
        Log.d(TAG,"Activity started")
    }
}
