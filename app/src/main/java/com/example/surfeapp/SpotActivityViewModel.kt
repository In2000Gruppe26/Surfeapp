package com.example.surfeapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class SpotActivityViewModel : ViewModel() {

    private val dataSource = DataSource()

    private val spot = MutableLiveData<Surfespot>()

    fun getSurfespot(): LiveData<Surfespot> {
        return spot
    }

    fun fetchSurfespot(context: Context, spotTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.getSpots(context).also {
                if (it != null) {
                    for (i in it.list){
                        if (i.name.equals(spotTitle)){
                            spot.postValue(i)
                        }
                    }
                }
            }
        }
    }

}