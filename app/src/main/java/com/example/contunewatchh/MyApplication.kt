package com.example.contunewatchh
import android.app.Application
import java.util.concurrent.Executors

class MyApplication: Application() {
    val executor = Executors.newSingleThreadExecutor()
}