package com.example.surfeapp

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.internal.ContextUtils.getActivity
import junit.framework.TestCase
import org.junit.Before
import java.lang.Boolean
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

import android.os.Build.VERSION_CODES.Q
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
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
        TestCase.assertEquals(0.2F, spot.getConditions().waveSize)
    }

    @Test
    fun testgetRating(){
        val spot:Surfespot = DataSource().getSpots(context)?.list?.get(0)!!
        //Denne testen er litt håpløs fordi den sammenligner en statisk verdi med en variabel. Så her må expected justeres ihh til ekte data.
        print(spot.getConditions())
        TestCase.assertEquals(1, spot.getRating())
    }
}