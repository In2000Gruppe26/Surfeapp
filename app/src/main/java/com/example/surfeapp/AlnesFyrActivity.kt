package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AlnesFyrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alnes_fyr)

        val actionbar = supportActionBar

        actionbar!!.title = "Et steinkast unna, du liksom"

        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}