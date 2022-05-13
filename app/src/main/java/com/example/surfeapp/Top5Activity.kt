package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Top5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top5)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Vanskelig å velge spot?"
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFA1DBF6")))
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val button1 = findViewById<Button>(R.id.b1)
        button1.setOnClickListener{
            val intent = Intent(this, HoddevikActivity::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.b2)
        button2.setOnClickListener{
            val intent = Intent(this, SolastrandaActivity::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.b3)

        button3.setOnClickListener{
            val intent = Intent(this, UnstadActivity::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<Button>(R.id.b4)

        button4.setOnClickListener{
            val intent = Intent(this, AlnesFyrActivity::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<Button>(R.id.b5)

        button5.setOnClickListener{
            val intent = Intent(this, HustadvikaActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true }
}