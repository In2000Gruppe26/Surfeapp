package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Solastranda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solastranda)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "En god nr 2"
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFA1DBF6")))
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}