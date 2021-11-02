package ru.spbstu.icc.kspt.lab2.continuewatch
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    var onScreen = true
    private lateinit var sharedPref: SharedPreferences

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
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        sharedPref = getSharedPreferences(SECONDS_ELAPSED, Context.MODE_PRIVATE)
        backgroundThread.start()
    }

    override fun onStart() {
        onScreen = true
        secondsElapsed = sharedPref.getInt(SECONDS_ELAPSED, secondsElapsed)
        super.onStart()
    }

    override fun onStop() {
        onScreen = false
        with(sharedPref.edit()) {
            putInt(SECONDS_ELAPSED, secondsElapsed)
            apply()
        }
        super.onStop()
    }

    companion object { const val SECONDS_ELAPSED = "Seconds elapsed" }

}
