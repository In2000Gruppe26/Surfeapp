package com.example.surfeapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs


class SpotActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)
         mJob = Job()

         supportActionBar?.setDisplayHomeAsUpEnabled(true)

         supportActionBar?.apply {
             setLogo(R.drawable.logo_5)
             setDisplayShowHomeEnabled(true)
             setDisplayUseLogoEnabled(true)
         }

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
         viewModel.getSurfespot().observe(this) {
             launch {
                 tekstNavn.text = it.name
                 tekstBes.text = it.deep_description.toString()

                 var cond = Conditions(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
                 var ratingRes = 0
                 val deferred = async(Dispatchers.Default) {
                     cond = it.getConditions()

                     ratingRes =
                         it.getRating((cond.waveSize ?: 0) as Float, (cond.waveSize ?: 0) as Float)

                 }
                 deferred.await()
                 rating.rating = ratingRes.toFloat()

                 tekstBolge1.text = cond.waveSize.toString() + " m"
                 tekstBolge2.text = cond.currentSpeed.toString() + " m/s"
                 tekstBolge3.text = cond.currentDirection.toString()

                 tekstVind1.text = cond.wind_speed.toString() + " m/s"

                 tekstVind2.text = cond.wind_from_direction.toString()

                 tekstTemp.text = cond.air_temperature.toString() + "°C"
                 fun degToW(num: Float): String {
                     val direction = abs(num)
                     val index = ((direction / 22.5) + .5).toInt()
                     val arr = arrayOf(
                         "N",
                         "NNØ",
                         "NØ",
                         "ØNØ",
                         "Ø",
                         "ØSØ",
                         "SØ",
                         "SSØ",
                         "S",
                         "SSV",
                         "SV",
                         "VSV",
                         "V",
                         "VNV",
                         "NV",
                         "NNV"
                     )
                     return (arr[(index % 16)])
                 }
                 tekstVind2.text = degToW(cond.wind_from_direction ?: 0f)

                 tekstBolge3.text = degToW(cond.currentDirection ?: 0f)

                 retning.rotation = cond.currentDirection ?: 0f

                 retningVind.rotation = cond.wind_from_direction ?: 0f
                 tekstNedbor.text = cond.precipitation_rate.toString() + " mm/t"
             }
         }

         viewModel.fetchSurfespot(applicationContext, spotTitle.dropLast(0))

    }
}