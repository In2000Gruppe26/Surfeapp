package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NybegynnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nybegynner)
        val actionbar = supportActionBar

        actionbar!!.title = "Nybegynner?"

        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}