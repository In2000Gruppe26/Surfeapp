package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Nybegynner : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nybegynner)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Nybegynner?"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}