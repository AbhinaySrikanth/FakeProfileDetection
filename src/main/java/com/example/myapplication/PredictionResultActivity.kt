package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PredictionResultActivity : AppCompatActivity() {

    private lateinit var genuineButton: Button
    private lateinit var fakeButton: Button
    private lateinit var predictTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prediction_result)

        genuineButton = findViewById(R.id.genuineButton)
        fakeButton = findViewById(R.id.fakeButton)
        predictTextView = findViewById(R.id.predictTextView)

        val prediction = intent.getIntExtra("prediction", -1)
        if (prediction == 1) {
            fakeButton.visibility = Button.VISIBLE
            genuineButton.visibility = Button.GONE
        } else if (prediction == 0) {
            genuineButton.visibility = Button.VISIBLE
            fakeButton.visibility = Button.GONE
        } else {
            genuineButton.visibility = Button.GONE
            fakeButton.visibility = Button.GONE
        }

        predictTextView.setOnClickListener {
            // Redirect to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
