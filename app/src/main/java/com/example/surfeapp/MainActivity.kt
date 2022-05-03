package com.example.surfeapp

//import android.databinding.DataBindingUtil

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.surfeapp.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener,
    OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    private var myMarker: Marker? = null

    companion object {
        var tokenSecret: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



        drawerLayout = findViewById(R.id.drawerLayout)

        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()

        navView = findViewById(R.id.navView)

        /*
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nybegynner -> {
                    Toast.makeText(this, "Nybegynner?", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tips -> {
                    Toast.makeText(this, "Tips & triks", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.om -> {
                    Toast.makeText(this, "Om oss", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    print("hei")
                    false
                }
            }
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnInfoWindowClickListener(this)

        mMap.setMinZoomPreference(6.0f)
        val starterScope = LatLng(61.140304, 8.542487)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(starterScope))

        val adelaideBounds = LatLngBounds(
            LatLng(57.456008, -9.808621),  // SW grenser
            LatLng(80.291513, 33.990307) // NE grenser
        )
        mMap.setLatLngBoundsForCameraTarget(adelaideBounds)
        val viewModel1: MainActivityViewModel by viewModels()

        viewModel1.getSurfespotsMain().observe(this) {
            var i = 0
            val size: Int = it.list.size
            while (i < size) {
                val longMain: Double = it.list[i].coordinates.longitude
                val latMain: Double = it.list[i].coordinates.latitude
                val nameCords: String = it.list[i].name
                val rating: Int = 1 //it.list[i].getRating()
                val temp = LatLng(latMain, longMain)
                mMap.addMarker(MarkerOptions().position(temp).title("$nameCords ($rating/5)").snippet(it.list[i].description).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.pin)))
                i++
            }
        }

        viewModel1.fetchSurfespotsMain(applicationContext)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            this.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    fun TilOmSiden(item: android.view.MenuItem) {
        val intent = Intent(this, OmSiden::class.java)
        startActivity(intent)
    }
    fun TilNybegynner(item: android.view.MenuItem) {
        val intent = Intent(this, Nybegynner::class.java)
        startActivity(intent)
    }
    fun TilTipsOgTriks(item: android.view.MenuItem) {
        val intent = Intent(this, TipsOgTriks::class.java)
        startActivity(intent)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onInfoWindowClick(p0: Marker) {
        val intent = Intent(this, SpotActivity::class.java)
        intent.putExtra("spotTitle", p0.title)
        startActivity(intent)
    }


}
