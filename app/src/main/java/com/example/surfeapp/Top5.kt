package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.surfeapp.Hoddevik
import com.example.surfeapp.Hustadvika
import com.example.surfeapp.Unstad
import kotlinx.android.synthetic.main.activity_top5.*

class Top5 : AppCompatActivity() {
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
            val intent = Intent(this, Hoddevik::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.b2)
        button2.setOnClickListener{
            val intent = Intent(this, Solastranda::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.b3)

        button3.setOnClickListener{
            val intent = Intent(this, Unstad::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<Button>(R.id.b4)

        button4.setOnClickListener{
            val intent = Intent(this, AlnesFyr::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<Button>(R.id.b5)

        button5.setOnClickListener{
            val intent = Intent(this, Hustadvika::class.java)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true }
}