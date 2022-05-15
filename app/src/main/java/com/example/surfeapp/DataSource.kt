package com.example.surfeapp

import android.content.Context
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import java.io.IOException
import kotlin.math.exp

class DataSource {
    fun onCreate(){

    }

    fun getConditions(spot:Surfespot):Conditions{
        // BESKRIVELSE
        // Når du bruker Surfespot.getConditions så kaller den egentlig bare på denne
        var urlOcean = "https://in2000-apiproxy.ifi.uio.no/weatherapi/oceanforecast/2.0/complete?"

        urlOcean += "lat=" + spot.coordinates.latitude.toString()
        urlOcean += "&lon=" + spot.coordinates.longitude.toString()

        var urlNow = "https://in2000-apiproxy.ifi.uio.no/weatherapi/nowcast/2.0/complete?"

        urlNow += "lat=" + spot.coordinates.latitude.toString()
        urlNow += "&lon=" + spot.coordinates.longitude.toString()

        val gson = Gson()
        var conditions = Conditions(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        runBlocking {
            try {

                val responseOcean = gson.fromJson(Fuel.get(urlOcean).awaitString(), Base::class.java)

                val responseNow = gson.fromJson(Fuel.get(urlNow).awaitString(), Base2::class.java)

                val waveSize:Float? = responseOcean.properties?.timeseries?.get(0)?.data?.instant?.details?.sea_surface_wave_height
                val currentSpeed:Float? = responseOcean.properties?.timeseries?.get(0)?.data?.instant?.details?.sea_water_speed
                val currentDirection:Float? = responseOcean.properties?.timeseries?.get(0)?.data?.instant?.details?.sea_water_to_direction

                val airTemperature = responseNow.properties?.timeseries?.get(0)?.data?.instant?.details?.air_temperature
                val precipitationRate = responseNow.properties?.timeseries?.get(0)?.data?.instant?.details?.precipitation_rate
                val windSpeed = responseNow.properties?.timeseries?.get(0)?.data?.instant?.details?.wind_speed
                val windFromDirection = responseNow.properties?.timeseries?.get(0)?.data?.instant?.details?.wind_from_direction

                conditions = Conditions(waveSize, currentSpeed, currentDirection, airTemperature, precipitationRate, windSpeed, windFromDirection)
                println(conditions.toString())
                println(spot.toString())
            } catch(exception: Exception) {
                println("A network request exception was thrown: ${exception.message}")
            }
        }

        return conditions
    }

    fun getRating(waveSize:Float, waveSpeed:Float):Int{
       // var conditions:Conditions = getConditions(spot)

        //val waveSize:Float = conditions.waveSize?.toFloat() ?: 0.toFloat()
        //val waveSpeed:Float = conditions.currentSpeed?.toFloat() ?: 0.toFloat()

        var j = -0.5691
        var tot:Float = 0.0.toFloat()
        var i = j
        
        val b1 = 1.4
        val b2 = -0.474
        
        val probabilities = mutableListOf<Float>()
        for(a in 3..7){
            if(a > 3){
                if(a == 7){
                    probabilities.add(a-3, 1 - tot)
                }else if(a == 4){
                    i = j + 1.2
                    probabilities.add(a-3, (exp(i-b1*waveSize-b2*waveSpeed)/(1+exp(i-b1*waveSize-b2*waveSpeed))-exp(j-b1*waveSize-b2*waveSpeed)/(1+exp(j-b1*waveSize-b2*waveSpeed))).toFloat())
                    tot += (exp(i - b1 * waveSize - b2 * waveSpeed) / (1 + exp(i - b1 * waveSize - b2 * waveSpeed)) - exp(j - b1 * waveSize - b2 * waveSpeed) / (1 + exp(j - b1 * waveSize - b2 * waveSpeed))).toFloat()
                }else{
                    i = j + 1.1
                    probabilities.add(a-3,(exp(i-b1*waveSize-b2*waveSpeed)/(1+exp(i-b1*waveSize-b2*waveSpeed))-exp(j-b1*waveSize-b2*waveSpeed)/(1+exp(j-b1*waveSize-b2*waveSpeed))).toFloat())
                    tot += (exp(i - b1 * waveSize - b2 * waveSpeed) / (1 + exp(i - b1 * waveSize - b2 * waveSpeed)) - exp(j - b1 * waveSize - b2 * waveSpeed) / (1 + exp(j - b1 * waveSize - b2 * waveSpeed))).toFloat()
                }
            }else{
                i = j
                probabilities.add(a-3, (exp(i-b1*waveSize-b2*waveSpeed)/(1+exp(i-b1*waveSize-b2*waveSpeed))).toFloat())
                tot += (exp(i - b1 * waveSize - b2 * waveSpeed) / (1 + exp(i - b1 * waveSize - b2 * waveSpeed))).toFloat()
            }
            j = i
        }

        var max:Float = 0.0.toFloat()
        var bestGuess = 0
        for((k, p) in probabilities.withIndex()){
            if(p > max){
                max = p
                bestGuess = k
            }
        }
        println(probabilities.toString())
        return bestGuess + 1
    }

    fun getSpots(context: Context): Spots? {
        // BESKRIVELSE
        // Kall på denne funksjonen for å få en liste med alle surfespot-objekte
        val gson = Gson()
        val jsonString: String
        return try {
            jsonString = context.assets.open("surfespots.json").bufferedReader().use { it.readText() }
            val response = gson.fromJson(jsonString, SpotsJson::class.java)
            println(response.toString())
            val convertedResponse:MutableList<Surfespot> = mutableListOf()
            for (i in response.list){
                val tmpSpot = Surfespot(i.id, i.name, Coordinates(i.latitude, i.longitude), i.description, i.deep_description)
                convertedResponse.add(tmpSpot)
            }
            val spots = Spots(convertedResponse)
            spots
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}

data class Base(val type: String?, val geometry: Geometry?, val properties: Properties?)

data class Data(val instant: Instant?)

data class Details(val sea_surface_wave_from_direction: Float?, val sea_surface_wave_height: Float?, val sea_water_speed: Float?, val sea_water_temperature: Float?, val sea_water_to_direction: Float?)

data class Geometry(val type: String?, val coordinates: List<Number>?)

data class Instant(val details: Details?)

data class Meta(val updated_at: String?, val units: Units?)

data class Properties(val meta: Meta?, val timeseries: List<Timeseries139117139>?)

data class Timeseries139117139(val time: String?, val data: Data?)

data class Units(val sea_surface_wave_from_direction: String?, val sea_surface_wave_height: String?, val sea_water_speed: String?, val sea_water_temperature: String?, val sea_water_to_direction: String?)

data class SurfespotJson(val id: Int, val name: String, val latitude: Double, val longitude: Double, val description: String?, val deep_description: String?)

data class SpotsJson(val list: List<SurfespotJson>)





data class Base2(val type: String?, val geometry: Geometry2?, val properties: Properties2?)

data class Data2(val instant: Instant2?, val next_1_hours: Next_1_hours2?)

data class Details2(val air_temperature: Float?, val precipitation_rate: Float?, val relative_humidity: Float?, val wind_from_direction: Float?, val wind_speed: Float?, val wind_speed_of_gust: Float?)

data class Geometry2(val type: String?, val coordinates: List<Number>?)

data class Instant2(val details: Details2?)

data class Meta2(val updated_at: String?, val units: Units2?, val radar_coverage: String?)

data class Next_1_hours2(val summary: Summary2?, val details: Details2?)

data class Properties2(val meta: Meta2?, val timeseries: List<Timeseries5905723>?)

data class Summary2(val symbol_code: String?)

data class Timeseries5905723(val time: String?, val data: Data2?)

data class Units2(val air_temperature: String?, val precipitation_amount: String?, val precipitation_rate: String?, val relative_humidity: String?, val wind_from_direction: String?, val wind_speed: String?, val wind_speed_of_gust: String?)



