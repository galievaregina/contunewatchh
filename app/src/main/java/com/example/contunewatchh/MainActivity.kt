package com.example.contunewatchh

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    private val APP = "counter"
    lateinit var pref: SharedPreferences

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(APP, MODE_PRIVATE)
        if (pref.contains(TIME_SCORE)) {
            //Получаем число из настроек
            secondsElapsed = pref.getInt(TIME_SCORE, 0);
        }
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onStart() {
        super.onStart()
        if (pref.contains(TIME_SCORE)) {
            //Получаем число из настроек
            secondsElapsed = pref.getInt(TIME_SCORE, 0);
        }
    }

    override fun onStop() {
        super.onStop()
        with(pref.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(TIME_SCORE, secondsElapsed)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            secondsElapsed = getInt(TIME_SCORE)
        }
    }
}
