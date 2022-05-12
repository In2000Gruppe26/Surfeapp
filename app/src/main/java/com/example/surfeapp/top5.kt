package com.example.surfeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class top5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top5)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Vanskelig å velge spot?"
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFA1DBF6")))
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun TilHoddevik(view: View) {
        val intent = Intent(this, Hoddevik::class.java)
        startActivity(intent)}

    fun TilSolastranden(view: View) {
        val intent = Intent(this, Solastranda::class.java)
        startActivity(intent)}

    fun TilUnstad(view: View) {
        val intent = Intent(this, Unstad::class.java)
        startActivity(intent)}

    fun TilHustadvika(view: View) {
        val intent = Intent(this, Hustadvika::class.java)
        startActivity(intent)}

    fun TilAlnes(view: View) {
        val intent = Intent(this, AlnesFyr::class.java)
        startActivity(intent)}
}