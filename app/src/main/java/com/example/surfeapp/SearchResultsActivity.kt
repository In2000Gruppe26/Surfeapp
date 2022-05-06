package com.example.surfeapp

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity


class SearchResultsActivity : AppCompatActivity() {
    private val tableLayout: TableLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        handleIntent(intent)

        Log.d("TAG", "message")
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