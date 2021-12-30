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
import java.util.concurrent.Future


class ExecutorSolution : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = "state"
    private val service = MyApplication().executor
    private lateinit var future:Future<*>


    private val backgroundThread = Runnable {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(TIME_SCORE, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        future.cancel(true)
        Log.d(TAG, "Activity stopped")
        super.onStop()

    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(TIME_SCORE, secondsElapsed)
        future = service.submit(backgroundThread)
        Log.d(TAG,"Activity started")
        super.onStart()
    }

}