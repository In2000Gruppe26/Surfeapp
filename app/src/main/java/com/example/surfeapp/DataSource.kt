package com.example.surfeapp

import android.location.Location
import android.location.LocationManager

class DataSource {

    public fun getConditions(spot:Surfespot):Conditions{
        // BESKRIVELSE
        // Når du bruker Surfespot.getConditions så kaller den egentlig bare på denne

        return Conditions(1, 2, 3)
    }
    public fun getSpots():Spots{
        // BESKRIVELSE
        // Kall på denne funksjonen for å få en liste med alle surfespot-objekter

        val posisjon:Location = Location(LocationManager.GPS_PROVIDER)
        posisjon.latitude = 58.8849857
        posisjon.longitude = 5.60265
        val eksempelSpot:Surfespot = Surfespot(0, "Solastranden", posisjon, "Et eksempel på en beskrivelse.")

        val spots:Spots = Spots(listOf<Surfespot>(eksempelSpot))

        return spots
    }
}