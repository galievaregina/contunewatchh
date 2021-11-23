package com.example.contunewatchh

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val TIME_SCORE = "time"
    private var counting = true

    var backgroundThread = Thread {
        while (true) {
            if (counting) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                secondsElapsed = getInt(TIME_SCORE)
            }
        }
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onResume() {
        counting = true
        super.onResume()
    }

    override fun onPause() {
        counting = false
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(TIME_SCORE, secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        secondsElapsed = savedInstanceState.getInt(TIME_SCORE)
        super.onRestoreInstanceState(savedInstanceState)
    }
}