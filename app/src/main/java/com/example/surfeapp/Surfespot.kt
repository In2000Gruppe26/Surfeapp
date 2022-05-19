package com.example.surfeapp

data class Coordinates(val latitude: Double, val longitude: Double)

data class Surfespot(val id: Int, val name: String, val coordinates: Coordinates, val description: String?, val deep_description: String?)
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

fun getRating(waveSize: Float, currentSpeed: Float): Int {
    return DataSource().getRating(waveSize, currentSpeed)
}
    // BESKRIVELSE
    // Funksjon som returnerer rating av forholdene på spottet.
    // funksjonen returnerer et tall mellom 1 og 5.

data class Conditions(var waveSize: Float?, val currentSpeed: Float?, val currentDirection: Float?, val air_temperature: Float?, val precipitation_rate: Float?, val wind_speed: Float?, val wind_from_direction: Float?)
    // BESKRIVELSE
    // Dette er et objekt med værforhold
