package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OmSidenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_om_siden)

        val actionbar = supportActionBar

        actionbar!!.title = "Om oss"

        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}