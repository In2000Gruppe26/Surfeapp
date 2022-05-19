package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TipsOgTriksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips_og_triks)

        val actionbar = supportActionBar

        actionbar!!.title = "Tips og triks"

        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}