package com.example.surfeapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs


class SpotActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)

         // Har satt denne aktivititeten sin parent som MainActivity i Android Manifest, slik at man kommer tilbake til den
         // ved å trykke på denne
         supportActionBar?.setDisplayHomeAsUpEnabled(true)

         //Denne siden skal vise et valgt surfespot. (V)

         //actionbarlogo
         supportActionBar?.apply {
             setLogo(R.drawable.logo_5)
             setDisplayShowHomeEnabled(true)
             setDisplayUseLogoEnabled(true)
         }
         //ok

         val viewModel: SpotActivityViewModel by viewModels()

         val tekstNavn = findViewById<TextView>(R.id.navnTekst)
         val tekstBolge1 = findViewById<TextView>(R.id.bolgeTekst1)
         val tekstBolge2 = findViewById<TextView>(R.id.bolgeTekst2)
         val tekstBolge3 = findViewById<TextView>(R.id.bolgeTekst3)
         val retning = findViewById<ImageView>(R.id.retning)
         val tekstVind1 = findViewById<TextView>(R.id.vindTekst1)
         val tekstVind2 = findViewById<TextView>(R.id.vindTekst2)
         val retningVind = findViewById<ImageView>(R.id.retningVind)
         val tekstTemp = findViewById<TextView>(R.id.tempTekst)
         val tekstNedbor = findViewById<TextView>(R.id.nedbor)
         val tekstBes = findViewById<TextView>(R.id.besTekst)
         val rating = findViewById<RatingBar>(R.id.rating1)


         val spotTitle: String = intent.extras?.getString("spotTitle") ?: ""
         //VIKTIG: FIKSE SÅ DEN FINNER RIKTIG SPOT
         viewModel.getSurfespot().observe(this) {
             tekstNavn.text = it.name
             val cond = it.getConditions()

             val ratingRes = it.getRating((cond.waveSize ?: 0) as Float, (cond.waveSize ?: 0) as Float)
             rating.rating = ratingRes.toFloat()

             tekstBolge1.text = cond.waveSize.toString() + " m"
             tekstBolge2.text = cond.currentSpeed.toString() + " m/s"
             tekstBolge3.text = cond.currentDirection.toString()

             tekstVind1.text = cond.wind_speed.toString()  + " m/s"

             tekstVind2.text = cond.wind_from_direction.toString()

             tekstTemp.text = cond.air_temperature.toString()  + "°C"
             tekstBes.text = it.deep_description.toString()
             fun degToW(num: Float): String{
                 val direction = abs(num)
                 val index = ((direction/22.5)+.5).toInt()
                 val arr = arrayOf("N","NNØ","NØ","ØNØ","Ø","ØSØ", "SØ", "SSØ","S","SSV","SV","VSV","V","VNV","NV","NNV")
                 return(arr[(index % 16)])
             }
             tekstVind2.text = degToW(cond.wind_from_direction ?: 0f)

             tekstBolge3.text = degToW(cond.currentDirection ?: 0f)

             retning.rotation = cond.currentDirection ?: 0f

             retningVind.rotation = cond.wind_from_direction ?: 0f
             tekstNedbor.text = cond.precipitation_rate.toString() + " mm/t"
         }

         viewModel.fetchSurfespot(applicationContext, spotTitle.dropLast(0))

    }
}