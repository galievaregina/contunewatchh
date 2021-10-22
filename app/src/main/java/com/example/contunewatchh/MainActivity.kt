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

    override fun onPause(){
        pref = getSharedPreferences(APP, MODE_PRIVATE) ?: return
        with(pref.edit()) {
            putInt(TIME_SCORE, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        super.onPause()
    }

    override fun onResume() {
        pref = getSharedPreferences(APP, MODE_PRIVATE) ?: return
        secondsElapsed = pref.getInt(TIME_SCORE, 0);
        super.onResume()
    }
}
