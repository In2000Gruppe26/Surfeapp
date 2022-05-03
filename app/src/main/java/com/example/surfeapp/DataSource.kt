package com.example.surfeapp

import android.content.Context
import com.example.surfeapp.MainActivity.Companion.tokenSecret
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.coroutines.awaitResponse
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.IOException
import kotlin.math.exp

class DataSource {
//CLIENT ID FROST: af800469-bcec-450b-95c7-d7944ca2b73b
//CLIENT SECRET FROST: 0f39f1cf-033e-43a5-9602-f5855725a638
    private lateinit var token:Token

    public fun onCreate(){

    }

    public fun getConditions(spot:Surfespot):Conditions{
        //println(token.access_token)
        // BESKRIVELSE
        // Når du bruker Surfespot.getConditions så kaller den egentlig bare på denne

        // LEGGE TIL ASYNKRON GET MED FUEL HER <------------
        var url = "https://in2000-apiproxy.ifi.uio.no/weatherapi/oceanforecast/2.0/complete?"

        url += "lat=" + spot.coordinates.latitude.toString()
        url += "&lon=" + spot.coordinates.longitude.toString()

        var url2 = "https://in2000-apiproxy.ifi.uio.no/weatherapi/nowcast/2.0/complete?"

        url2 += "lat=" + spot.coordinates.latitude.toString()
        url2 += "&lon=" + spot.coordinates.longitude.toString()

        val gson = Gson()
        var conditions:Conditions = Conditions(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        runBlocking {
            try {

                val response = gson.fromJson(Fuel.get(url).awaitString(), Base::class.java)

                val response2 = gson.fromJson(Fuel.get(url2).awaitString(), Base2::class.java)

                val wavesize:Float? = response.properties?.timeseries?.get(0)?.data?.instant?.details?.sea_surface_wave_height
                val currentspeed:Float? = response.properties?.timeseries?.get(0)?.data?.instant?.details?.sea_water_speed
                val currentdirection:Float? = response.properties?.timeseries?.get(0)?.data?.instant?.details?.sea_water_to_direction

                val air_temperature = response2.properties?.timeseries?.get(0)?.data?.instant?.details?.air_temperature
                val precipitation_rate = response2.properties?.timeseries?.get(0)?.data?.instant?.details?.precipitation_rate
                val wind_speed = response2.properties?.timeseries?.get(0)?.data?.instant?.details?.wind_speed
                val wind_from_direction = response2.properties?.timeseries?.get(0)?.data?.instant?.details?.wind_from_direction

                conditions = Conditions(wavesize, currentspeed, currentdirection, air_temperature, precipitation_rate, wind_speed, wind_from_direction)
                println(conditions.toString())
                println(spot.toString())
            } catch(exception: Exception) {
                println("A network request exception was thrown: ${exception.message}")
            }
        }

        return conditions
    }

    public fun getRating(spot:Surfespot):Int{
        var conditions:Conditions = getConditions(spot)

        val waveSize:Float = conditions.waveSize?.toFloat() ?: 0.toFloat()
        val waveSpeed:Float = conditions.currentSpeed?.toFloat() ?: 0.toFloat()

        var j = -0.5691
        var tot:Float = 0.0.toFloat()
        var i = j
        
        val B_1 = 1.4
        val B_2 = -0.474
        
        var probabilities = mutableListOf<Float>()
        for(a in 3..7){
            if(a > 3){
                if(a == 7){
                    probabilities.add(a-3, 1 - tot)
                }else if(a == 4){
                    i = j + 1.2
                    probabilities.add(a-3, (exp(i-B_1*waveSize-B_2*waveSpeed)/(1+exp(i-B_1*waveSize-B_2*waveSpeed))-exp(j-B_1*waveSize-B_2*waveSpeed)/(1+exp(j-B_1*waveSize-B_2*waveSpeed))).toFloat())
                    tot = tot + (exp(i-B_1*waveSize-B_2*waveSpeed)/(1+exp(i-B_1*waveSize-B_2*waveSpeed))-exp(j-B_1*waveSize-B_2*waveSpeed)/(1+exp(j-B_1*waveSize-B_2*waveSpeed))).toFloat()
                }else{
                    i = j + 1.1
                    probabilities.add(a-3,(exp(i-B_1*waveSize-B_2*waveSpeed)/(1+exp(i-B_1*waveSize-B_2*waveSpeed))-exp(j-B_1*waveSize-B_2*waveSpeed)/(1+exp(j-B_1*waveSize-B_2*waveSpeed))).toFloat())
                    tot = tot + (exp(i-B_1*waveSize-B_2*waveSpeed)/(1+exp(i-B_1*waveSize-B_2*waveSpeed))-exp(j-B_1*waveSize-B_2*waveSpeed)/(1+exp(j-B_1*waveSize-B_2*waveSpeed))).toFloat()

                }
            }else{
                i = j
                probabilities.add(a-3, (exp(i-B_1*waveSize-B_2*waveSpeed)/(1+exp(i-B_1*waveSize-B_2*waveSpeed))).toFloat())
                tot = tot + (exp(i-B_1*waveSize-B_2*waveSpeed)/(1+exp(i-B_1*waveSize-B_2*waveSpeed))).toFloat()
            }
            j = i
        }

        var max:Float = 0.0.toFloat()
        var bestGuess = 0
        var k:Int = 0
        for(p in probabilities){
            if(p > max){
                max = p
                bestGuess = k
            }
            k++
        }
        println(probabilities.toString())
        return bestGuess + 1
    }

    public suspend fun getSpots(context: Context): Spots? {
        // BESKRIVELSE
        // Kall på denne funksjonen for å få en liste med alle surfespot-objekte
        println("TEST")
        val gson = Gson()
        if(tokenSecret.isNullOrEmpty()) {
            val url1 = "https://id.barentswatch.no/connect/token"
            //val response1 = gson.fromJson(Fuel.post(url).awaitString(), Token::class.java)
//ewkjgndqer87vc
            val (request, response, result) =
                Fuel.post(url1).header(Headers.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .body("client_id=alfredlovgr1%40gmail.com%3Asurfeapp&scope=api&client_secret=ewkjgndqer87vc&grant_type=client_credentials")
                    .responseString()
            val (token, err) = result
            tokenSecret = gson.fromJson(token, Token::class.java).access_token.toString()

            val gson = Gson()
            val body = Base3("2022-04-14T15:00:00Z", listOf(RoutePoints595846129(68.2687586, 13.5810711, "2022-04-14T15:00:00Z", "2022-04-14T15:00:00Z")))
            val json = gson.toJson(body)
            val answer = JSONObject("""{ "forecastDatetime": "2022-04-14T15:06:58.073Z","routePoints": [ {"lat": 0,"lon": 0,"passingTime": "2022-04-14T15:06:58.073Z","forecastDatetime": "2022-04-14T15:06:58.073Z" }]}""")

            println(Fuel.post("https://www.barentswatch.no/bwapi/v1/geodata/waveforecast/route")
                .body(answer.toString()).appendHeader("Authorization", "bearer $tokenSecret" as Any).appendHeader("Accept", "application/json").appendHeader("Content-Type", "text/json")
                .responseString())
            //println(Fuel.get("https://www.barentswatch.no/bwapi/v2/geodata/waveforecast/fairway").appendHeader("Authorization", "bearer $tokenSecret" as Any).responseString())
        }

        val jsonString: String

        try {
            jsonString = context.assets.open("surfespots.json").bufferedReader().use { it.readText() }
            val response = gson.fromJson(jsonString, Spots_json::class.java)
            println(response.toString())
            var converted_response:MutableList<Surfespot> = mutableListOf()
            for (i in response.list){
                val tmp_spot:Surfespot = Surfespot(i.id, i.name, Coordinates(i.latitude, i.longitude), i.description)
                converted_response.add(tmp_spot)
            }
            val spots: Spots = Spots(converted_response)
            return spots
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
    }
}

// result generated from /json

data class Base(val type: String?, val geometry: Geometry?, val properties: Properties?)

data class Data(val instant: Instant?)

data class Details(val sea_surface_wave_from_direction: Float?, val sea_surface_wave_height: Float?, val sea_water_speed: Float?, val sea_water_temperature: Float?, val sea_water_to_direction: Float?)

data class Geometry(val type: String?, val coordinates: List<Number>?)

data class Instant(val details: Details?)

data class Meta(val updated_at: String?, val units: Units?)

data class Properties(val meta: Meta?, val timeseries: List<Timeseries139117139>?)

data class Timeseries139117139(val time: String?, val data: Data?)

data class Units(val sea_surface_wave_from_direction: String?, val sea_surface_wave_height: String?, val sea_water_speed: String?, val sea_water_temperature: String?, val sea_water_to_direction: String?)

data class Surfespot_json(val id: Int, val name: String, val latitude: Double, val longitude: Double, val description: String?)

data class Spots_json(val list: List<Surfespot_json>)





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

data class Token(val access_token: String?, val token_type: String?, val expires_in: Number?){
    //User Deserializer
    class Deserializer : ResponseDeserializable<Token> {
        override fun deserialize(content: String) = Gson().fromJson(content, Token::class.java)
    }
}

// result generated from /json

data class Base3(val forecastDatetime: String?, val routePoints: List<RoutePoints595846129>?)

data class RoutePoints595846129(val lat: Number?, val lon: Number?, val passingTime: String?, val forecastDatetime: String?)