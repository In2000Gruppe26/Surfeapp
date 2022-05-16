package com.example.surfeapp

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.surfeapp.databinding.ActivitySearchResultsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SearchResultsActivity : AppCompatActivity() {
    private val tableLayout: TableLayout? = null
    private lateinit var binding: ActivitySearchResultsBinding

    lateinit var adapter: ArrayAdapter<*>

    private lateinit var listViewSearch: ListView
    private lateinit var emptyView: TextView
    lateinit var spotList: Spots

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        handleIntent(intent)


        val viewModel1: SearchActivityViewModel by viewModels()

        viewModel1.getSurfespotsMain().observe(this) {

            spotList = it
            listViewSearch = findViewById(R.id.listView_search)
            emptyView = findViewById(R.id.emptyView)
            adapter = ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1,
                spotList.list.map { it.name }.toTypedArray())
            listViewSearch.adapter = adapter
            listViewSearch.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->

                }
            listViewSearch.emptyView = emptyView
        }

        viewModel1.fetchSurfespotsMain(applicationContext)

        Log.d("TAG", "message")

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                //doMySearch(query)
            }
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
        Log.d("TAG", "message")
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("TAG", "message")
            println(query.toString())
        }
    }
}