package com.example.surfeapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TipsOgTriksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips_og_triks)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Tips og triks"
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