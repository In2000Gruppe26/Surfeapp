package com.example.surfeapp

import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi

import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import java.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class DataSource {
//CLIENT ID FROST: af800469-bcec-450b-95c7-d7944ca2b73b
//CLIENT SECRET FROST: 0f39f1cf-033e-43a5-9602-f5855725a638

    public fun getConditions(spot:Surfespot):Conditions{
        // BESKRIVELSE
        // Når du bruker Surfespot.getConditions så kaller den egentlig bare på denne

        // LEGGE TIL ASYNKRON GET MED FUEL HER <------------
        var url = "https://api.met.no/weatherapi/oceanforecast/2.0/complete?"
        url += "lat=" + "58.8849857"
        url += "&lon=" + "5.60265"


        return Conditions(1, 2, 3)
    }
    public fun getSpots():Spots{
        // BESKRIVELSE
        // Kall på denne funksjonen for å få en liste med alle surfespot-objekter

        val posisjon:Location = Location(LocationManager.GPS_PROVIDER)
        posisjon.latitude = 5.60265
        posisjon.longitude = 58.8849857
        val eksempelSpot:Surfespot = Surfespot(0, "Solastranden", posisjon, "Et eksempel på en beskrivelse.")

        val spots:Spots = Spots(listOf<Surfespot>(eksempelSpot))

        return spots
    }
}