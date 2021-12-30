package com.example.contunewatchh

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.util.Log

class Threads : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = "state"
    private lateinit var thread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(TIME_SCORE, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        Log.d(TAG,"Activity created")
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        thread.interrupt()
        Log.d(TAG,"Activity stopped")
        super.onStop()
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(TIME_SCORE, secondsElapsed)
        val backgroundThread = Thread {
            Thread.currentThread().name = "Thread" + Thread.currentThread().id
            Log.d(TAG, "Created thread "+ Thread.currentThread().name)
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    Log.d(TAG, "Interrupted thread")
                }
            }
        }
        thread = backgroundThread
        thread.start()
        Log.d(TAG,"Activity started")
        super.onStart()
    }
}
