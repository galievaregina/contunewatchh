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
    lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    lateinit var sharedPreferences: SharedPreferences
    private val DELAY: Long = 1000
    private lateinit var handler:Handler
    val TAG = "state"


    private val backgroundThread: Runnable = object:Runnable{
        override fun run() {
            textSecondsElapsed.post{
                textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
            }
            handler.postDelayed(this, DELAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(TIME_SCORE, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        handler.removeCallbacks(backgroundThread)
        Log.d(TAG,"Thread stopped")
        super.onStop()
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(TIME_SCORE, secondsElapsed)
        backgroundThread.run()
        Log.d(TAG,"Thread started")
        super.onStart()
    }
}
