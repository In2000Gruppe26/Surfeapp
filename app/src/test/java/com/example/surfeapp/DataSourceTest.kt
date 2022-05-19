package com.example.surfeapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

import android.os.Build.VERSION_CODES.Q
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class DataSourceTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun testgetSpots(){
        println(DataSource().getSpots(context)?.list?.toString())
        TestCase.assertEquals(35,
            DataSource().getSpots(context)?.list?.count()
        )

    }

    @Test
    fun testgetConditions(){
        val spot:Surfespot = DataSource().getSpots(context)?.list?.get(0)!!
        //Denne testen er litt håpløs fordi den sammenligner en statisk verdi med en variabel. Så her må expected justeres ihh til ekte data.
        TestCase.assertEquals(2.4F, spot.getConditions().air_temperature)
    }

    @Test
    fun testgetRating(){
        val spot:Surfespot = DataSource().getSpots(context)?.list?.get(0)!!
        //Denne testen er litt håpløs fordi den sammenligner en statisk verdi med en variabel. Så her må expected justeres ihh til ekte data.
        val cond:Conditions = spot.getConditions()
        TestCase.assertEquals(1, getRating(
            (cond.waveSize ?: 0) as Float,
            (cond.currentSpeed ?: 0) as Float
        )
        )
    }
}