package com.example.surfeapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpotActivityViewModel : ViewModel() {
    //ViewModel (V)

    private val dataSource = DataSource()

    private val surfespots = MutableLiveData<Spots>()

    fun getSurfespots(): LiveData<Spots> {
        return surfespots
    }

    fun fetchSurfespots(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.getSpots(context).also {
                surfespots.postValue(it)
            }
        }
    }

}