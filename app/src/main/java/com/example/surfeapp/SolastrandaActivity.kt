package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SolastrandaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solastranda)

        val actionbar = supportActionBar

        actionbar!!.title = "En god nr 2"

        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}