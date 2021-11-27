package com.example.contunewatchh

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

class Coroutines : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(TIME_SCORE, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        lifecycleScope.launchWhenResumed {
            while (true) {
                delay(1000)
                textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
            }
        }
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        super.onStop()
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(TIME_SCORE, secondsElapsed)
        super.onStart()
    }
}
