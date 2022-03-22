package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels


class SpotActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)

         //Denne siden skal vise et valgt surfespot. (V)

         val viewModel: SpotActivityViewModel by viewModels()

         val tekstId = findViewById<TextView>(R.id.tekstId)
         val tekstName = findViewById<TextView>(R.id.tekstName)
         val tekstLocation = findViewById<TextView>(R.id.tekstLocation)
         val tekstDes = findViewById<TextView>(R.id.tekstDes)



         viewModel.getSurfespots().observe(this) {
             tekstId.text = it.list[0].id.toString()
             tekstName.text = it.list[0].name
             tekstLocation.text = it.list[0].coordinates.toString()
             tekstDes.text = it.list[0].description.toString()
         }


         viewModel.fetchSurfespots()


    }
}