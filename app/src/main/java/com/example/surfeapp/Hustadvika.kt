package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Hustadvika : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hustadvika)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Kanskje dette er nr. 1 likevel"
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