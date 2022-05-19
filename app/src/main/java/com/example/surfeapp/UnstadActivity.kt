package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UnstadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unstad)

        val actionbar = supportActionBar

        actionbar!!.title = "Unstad på 3. plass?"

        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}