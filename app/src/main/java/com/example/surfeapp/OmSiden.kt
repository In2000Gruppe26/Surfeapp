package com.example.surfeapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor()))
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}