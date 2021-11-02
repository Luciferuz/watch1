package ru.spbstu.icc.kspt.lab2.continuewatch
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    var onScreen = true

    var backgroundThread = Thread {
        while (true) {
            if (onScreen) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondsElapsed = savedInstanceState?.getInt(SECONDS_ELAPSED) ?: 0
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onStart() {
        super.onStart()
        onScreen = true
    }

    override fun onStop() {
        super.onStop()
        onScreen = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SECONDS_ELAPSED, secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    companion object { const val SECONDS_ELAPSED = "Seconds elapsed" }

}
