package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.viewModels
import org.w3c.dom.Text


class SpotActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)

         // Har satt denne aktivititeten sin parent som MainActivity i Android Manifest, slik at man kommer tilbake til den
         // ved å trykke på denne
         supportActionBar?.setDisplayHomeAsUpEnabled(true)

         //Denne siden skal vise et valgt surfespot. (V)

         val viewModel: SpotActivityViewModel by viewModels()

         val tekstNavn = findViewById<TextView>(R.id.navnTekst)
         val tekstBolge1 = findViewById<TextView>(R.id.bolgeTekst1)
         val tekstBolge2 = findViewById<TextView>(R.id.bolgeTekst2)
         val tekstBolge3 = findViewById<TextView>(R.id.bolgeTekst3)
         val tekstVind1 = findViewById<TextView>(R.id.vindTekst1)
         val tekstVind2 = findViewById<TextView>(R.id.vindTekst2)
         val tekstTemp = findViewById<TextView>(R.id.tempTekst)
         val tekstBes = findViewById<TextView>(R.id.besTekst)
         val rating = findViewById<RatingBar>(R.id.rating1)

         val spotTitle: String = intent.extras?.getString("spotTitle") ?: ""

         viewModel.getSurfespots().observe(this) {
             val strand = it.list[1]
             val ratingRes = strand.getRating()
             rating.rating = ratingRes.toFloat()
             tekstNavn.text = strand.name
             val cond = strand.getConditions()
             tekstBolge1.text = cond.waveSize.toString()
             tekstBolge2.text = cond.currentSpeed.toString()
             tekstBolge3.text = cond.currentDirection.toString()
             tekstVind1.text = cond.wind_speed.toString()
             tekstVind2.text = cond.wind_from_direction.toString()
             tekstTemp.text = cond.air_temperature.toString()
             tekstBes.text = it.list[0].description.toString()
         }


         viewModel.fetchSurfespots(applicationContext)


    }
}