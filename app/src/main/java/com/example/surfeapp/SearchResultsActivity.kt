package com.example.surfeapp

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SearchResultsActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<*>

    private lateinit var listViewSearch: ListView
    private lateinit var emptyView: TextView

    private lateinit var searchManager: SearchView
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val actionbar = supportActionBar

        actionbar!!.title = "Søkeresultat"

        actionbar.setDisplayHomeAsUpEnabled(true)

        val spotsarray:Array<String> = intent.getStringArrayExtra("spotList")!!

        searchQuery = intent.extras?.getString("query").toString()

        adapter = ArrayAdapter<Any>(this, android.R.layout.simple_list_item_1,
            spotsarray
        )
        listViewSearch = findViewById(R.id.listView_search)
        emptyView = findViewById(R.id.emptyView)
        adapter.filter.filter(searchQuery)

        listViewSearch.adapter = adapter
        listViewSearch.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, i, _ ->
                val intent = Intent(this, SpotActivity::class.java)
                intent.putExtra("spotTitle", listViewSearch.adapter.getItem(i).toString())
                startActivity(intent)
            }
        listViewSearch.emptyView = emptyView

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                searchQuery = query
                doMySearch(query)
            }
        }
    }

    private fun doMySearch(query: String){
        adapter.filter.filter(query)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        searchManager = menu.findItem(R.id.search).actionView as SearchView
        searchManager.isIconified = false
        searchManager.isIconifiedByDefault = false

        searchManager.setQuery(searchQuery, false)
        searchManager.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}