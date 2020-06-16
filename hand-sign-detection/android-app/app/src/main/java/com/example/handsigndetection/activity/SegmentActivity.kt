package com.example.handsigndetection.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handsigndetection.R
import com.example.handsigndetection.adapters.SegmentAdapters
import com.example.handsigndetection.model.Segment
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Prediction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_segment.*

class SegmentActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SegmentAdapters.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segment)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter =
            SegmentAdapters(getDataList())
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent: Intent = Intent(applicationContext, NewSegmentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDataList(): ArrayList<Segment>  {
        var predictions: ArrayList<Prediction> = ArrayList()
        predictions.add(Prediction("N", "0.96"))
        predictions.add(Prediction("M", "0.86"))
        predictions.add(Prediction("0", "0.76"))

        var images: ArrayList<Image> = ArrayList()
        images.add(Image(R.drawable.n, predictions))
        images.add(Image(R.drawable.e, predictions))
        images.add(Image(R.drawable.w, predictions))

        var segments: ArrayList<Segment> = ArrayList()
        segments.add(Segment("22 June 2020", "NEW", "0.87", images))
        segments.add(Segment("22 June 2020", "NEW", "0.87", images))

        return segments
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
}