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

         val viewModel: SpotActivityViewModel by viewModels()

         val tekstNavn = findViewById<TextView>(R.id.navnTekst)
         val tekstBolge1 = findViewById<TextView>(R.id.bolgeTekst1)
         val tekstBolge2 = findViewById<TextView>(R.id.bolgeTekst2)
         val tekstBolge3 = findViewById<TextView>(R.id.bolgeTekst3)
         val retning = findViewById<ImageView>(R.id.retning)
         val tekstVind1 = findViewById<TextView>(R.id.vindTekst1)
         val tekstVind2 = findViewById<TextView>(R.id.vindTekst2)
         val tekstTemp = findViewById<TextView>(R.id.tempTekst)
         val tekstBes = findViewById<TextView>(R.id.besTekst)
         val rating = findViewById<RatingBar>(R.id.rating1)

         //for actionbar
       //  supportActionBar?.apply {
       //      setDisplayShowHomeEnabled(true)
        //    setDisplayUseLogoEnabled(true)
         //   setDisplayShowTitleEnabled(false)
       //      setLogo(R.drawable.ferdig_logo)

      //   }

         val spotTitle: String = intent.extras?.getString("spotTitle") ?: ""
         //VIKTIG: FIKSE SÅ DEN FINNER RIKTIG SPOT
         viewModel.getSurfespots().observe(this) {
             var strand:Surfespot
             for (i in it.list){
                 println(i.name)
                 println(spotTitle)
                 if (i.name.equals(spotTitle.dropLast(6))){

                     strand = i
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
                     tekstBes.text = strand.description.toString()
                     fun degToW(num: Float): String{
                         val direction = abs(num)
                         val index = ((direction/22.5)+.5).toInt()
                         val arr = arrayOf("N","NNØ","NØ","ØNØ","Ø","ØSØ", "SØ", "SSØ","S","SSV","SV","VSV","V","VNV","NV","NNV")
                         return(arr[(index % 16)])
                     }

                     tekstBolge3.text = degToW(cond.currentDirection ?: 0f)
                     
                     retning.rotation = cond.currentDirection ?: 0f
                 }
             }


         }


         viewModel.fetchSurfespots(applicationContext)


    }
}