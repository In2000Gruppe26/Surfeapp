package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.surfeapp.R
//import android.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.surfeapp.databinding.ActivityMainBinding
import com.google.android.material.internal.ViewUtils.getContentView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding = DataBindingUtil.setContentView(this, getContentView());
        //Midlertidig button til SpotActivity (V)

        val knapp = findViewById<Button>(R.id.buttonTilSpot)
        knapp.setOnClickListener() {
            val intent = Intent(this, SpotActivity::class.java)
            startActivity(intent)
            }

        // Pass the ActionBarToggle action into the drawerListener
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


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val Hustadvika = LatLng(59.9174938, 10.7115087)
        mMap.addMarker(MarkerOptions().position(Hustadvika).title("Bolig til hunkmaster69420@ghotmail.com"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Hustadvika))
    }

    // override the onSupportNavigateUp() function to launch the Drawer when the hamburger icon is clicked

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onSupportNavigateUp(): Boolean {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            this.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

}
//<activity
//            android:name=".MapsActivity"
//            android:exported="false" />