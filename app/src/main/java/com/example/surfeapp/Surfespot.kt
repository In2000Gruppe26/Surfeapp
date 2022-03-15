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