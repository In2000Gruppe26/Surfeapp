package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
//import android.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.surfeapp.databinding.ActivityMainBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.navigation.NavigationView
import com.google.android.gms.maps.model.LatLngBounds
import android.content.res.Resources
import android.util.Log
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.material.internal.ContextUtils.getActivity


class MainActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener,
    OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    private var myMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawerLayout)

        actionBarToggle = ActionBarDrawerToggle(this, binding.root, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()

        navView = findViewById(R.id.navView)

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
                    false
                }
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val viewModel1: MainActivityViewModel by viewModels()

        viewModel1.getSurfespotsMain().observe(this) {
            var i = 0
            val size: Int = it.list.size
            while (i < size) {
                val longMain: Double = it.list[i].coordinates.longitude
                val latMain: Double = it.list[i].coordinates.latitude
                val nameCords: String = it.list[i].name
                val rating: Int = it.list[i].getRating()
                val temp = LatLng(latMain, longMain)
                //println("$nameCords ($rating/5)")
                mMap.addMarker(MarkerOptions().position(temp).title("$nameCords ($rating/5)").snippet(it.list[i].description))

                /* MARKER MED ICON:    mMap.addMarker(MarkerOptions().position(temp).title(nameCords).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.bolge2)))*/
                i++
            }
        }

        viewModel1.fetchSurfespotsMain(applicationContext)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnInfoWindowClickListener(this)


        /*try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_json
                )
            )
            if (!success) {
                Log.e("Error", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("Error", "Can't find style. Error: ", e)
        }*/

        mMap.setMinZoomPreference(6.0f)
        val starterScope = LatLng(61.140304, 8.542487)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(starterScope))

        val adelaideBounds = LatLngBounds(
            LatLng(57.456008, -9.808621),  // SW grenser
            LatLng(80.291513, 33.990307) // NE grenser
        )
        mMap.setLatLngBoundsForCameraTarget(adelaideBounds)

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
        Toast.makeText(applicationContext, "HEI", Toast.LENGTH_SHORT).show()
    }


}
