package com.example.surfeapp
import android.location.Location

data class Surfespot(val id: Int, val name: String, val location: Location, val description: String?)
    // BESKRIVELSE
    // Dette er 'Surfespot' objektet.
    // Hvert 'spot' har en id, et navn, en posisjon, og *kan* ha en beskrivelse
    // Location er en spesiell type object, se dokumetasjon på developers siden til android
    // BACKEND lager disse objektene.

data class Spots(val list: List<Surfespot>)
    // BESKRIVELSE
    // Dette er et 'spots' objekt
    // En liste som inneholder alle surfespots
    // Dette objektet fylles opp med Surfespots av backend

    // KEY: Du kan hente et surfespot fra listen etter at du har hentet listen fra DataSource

fun Surfespot.getConditions(): Conditions{
    return DataSource().getConditions(this)
}
    // BESKRIVELSE
    // Dette er en funksjon som utvider Surfespot så du kan få forholdene på det surfespottet ved å skrive:
    // navn_på_Surfespot_objekt.getConditions()


data class Conditions(val waveSize: Int, val currentSpeed: Int, val currentDirection: Int)
    // BESKRIVELSE
    // Dette er et objekt med værforhold
