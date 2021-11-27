package com.example.contunewatchh

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ExecutorSolution : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    lateinit var sharedPreferences: SharedPreferences
    private val DELAY: Long = 1000
    private lateinit var handler: Handler
    val TAG = "state"
    private lateinit var service: ExecutorService

    private val backgroundThread: Runnable = object : Runnable {
        override fun run() {
            if (!service.isShutdown) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
                }
                handler.postDelayed(this, DELAY)
            }
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
        service.shutdown()
        Log.d(TAG, "Thread stopped")
        super.onStop()
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(TIME_SCORE, secondsElapsed)
        service = MyApplication().executor
        service.execute(backgroundThread)
        Log.d(TAG, "Thread started")
        super.onStart()
    }

}