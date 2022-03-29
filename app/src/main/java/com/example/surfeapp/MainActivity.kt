package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //test
        // Funker det å pushe?
        // emilie
        // test2 Magnu
        //TESTVenus2

        //Midlertidig button til SpotActivity (V)
        

        val knapp = findViewById<Button>(R.id.buttonTilSpot)

        knapp.setOnClickListener() {
            val intent = Intent(this, SpotActivity::class.java)
            startActivity(intent)
        }
    }
}