package com.example.surfeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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



        val viewModel1: MainActivityViewModel by viewModels()

        viewModel1.getSurfespotsMain().observe(this) {
            var i = 0
            val size: Int = it.list.size
            while (i < size) {
                val longMain: Double = it.list[i].coordinates.longitude
                val latMain: Double = it.list[i].coordinates.latitude
                val nameCords: String = it.list[i].name
                val temp = LatLng(latMain, longMain)
                mMap.addMarker(MarkerOptions().position(temp).title(nameCords))
                i++
            }
        }

        viewModel1.fetchSurfespotsMain(applicationContext)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val Hustadvika = LatLng(59.9174938, 10.7115087)
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