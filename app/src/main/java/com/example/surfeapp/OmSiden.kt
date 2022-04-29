package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OmSiden : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_om_siden)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Om oss"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}