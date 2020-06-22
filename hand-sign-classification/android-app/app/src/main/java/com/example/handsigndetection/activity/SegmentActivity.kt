package com.example.handsigndetection.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handsigndetection.R
import com.example.handsigndetection.adapters.SegmentAdapters
import com.example.handsigndetection.handler.ImageHandler
import com.example.handsigndetection.handler.PredictionHandler
import com.example.handsigndetection.handler.SegmentHandler
import com.example.handsigndetection.model.Segment
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Prediction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_segment.*

class SegmentActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SegmentAdapters.ViewHolder>? = null
    private var segmentHandler: SegmentHandler? = null
    private var segments: ArrayList<Segment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segment)
        initRecyclerView()
        initAddButton()
    }


    // ALL ABOUT INITIALIZATION OF UI COMPONENT (START)
    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        segmentHandler = SegmentHandler(this)
        segments = segmentHandler?.readAll()
        adapter = SegmentAdapters(this, segments!!)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }
    // ALL ABOUT INITIALIZATION OF UI COMPONENT (END)



    // FLOATING ACTION BUTTON FUNCTION (START)
    private fun initAddButton() {
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent: Intent = Intent(this, NewSegmentActivity::class.java)
            startActivity(intent)
        }
    }
    // FLOATING ACTION BUTTON FUNCTION (END)



}