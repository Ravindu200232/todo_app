package com.mbexample.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbexample.alarmmanager.R
import com.mbexample.myapplication.ui.main.AlarmActivity

class MainActivity : AppCompatActivity() {
    private lateinit var add: Button
    private lateinit var deleteAll: Button
    private lateinit var sortBy: Button
    private lateinit var schedule: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: Adapter
    private var fullDataList: List<CardInfo> = listOf()
    private var filteredDataList: List<CardInfo> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views using findViewById
        add = findViewById(R.id.add)
        deleteAll = findViewById(R.id.deleteAll)
        sortBy = findViewById(R.id.sortBy)
        schedule = findViewById(R.id.schedule)
        recyclerView = findViewById(R.id.recycler_view)
        searchView = findViewById(R.id.searchView)

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize data
        fullDataList = DataObject.getAllData()  // Replace with your data fetching logic
        filteredDataList = fullDataList

        adapter = Adapter(filteredDataList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        deleteAll.setOnClickListener {
            DataObject.deleteAll()
            fullDataList = DataObject.getAllData()  // Refresh data list
            filter(searchView.query.toString())  // Reapply the current filter
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })

        sortBy.setOnClickListener {
            showSortDialog()
        }

        schedule.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }
    }

    private fun filter(query: String?) {
        filteredDataList = if (query.isNullOrEmpty()) {
            fullDataList
        } else {
            fullDataList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.Rdate.contains(query, ignoreCase = true) ||
                        it.Rtime.contains(query, ignoreCase = true)
            }
        }
        adapter.updateData(filteredDataList)
    }

    private fun showSortDialog() {
        val sortOptions = arrayOf("Date Ascending", "Date Descending", "Title Ascending", "Title Descending")
        AlertDialog.Builder(this)
            .setTitle("Sort By")
            .setItems(sortOptions) { dialog, which ->
                when (which) {
                    0 -> sortDataByDateAscending()
                    1 -> sortDataByDateDescending()
                    2 -> sortDataByTitleAscending()
                    3 -> sortDataByTitleDescending()
                }
            }
            .show()
    }

    private fun sortDataByDateAscending() {
        filteredDataList = filteredDataList.sortedBy { it.Rdate }  // Assume 'date' is a property in CardInfo
        adapter.updateData(filteredDataList)
    }

    private fun sortDataByDateDescending() {
        filteredDataList = filteredDataList.sortedByDescending { it.Rdate }  // Assume 'date' is a property in CardInfo
        adapter.updateData(filteredDataList)
    }

    private fun sortDataByTitleAscending() {
        filteredDataList = filteredDataList.sortedBy { it.title }  // Assume 'title' is a property in CardInfo
        adapter.updateData(filteredDataList)
    }

    private fun sortDataByTitleDescending() {
        filteredDataList = filteredDataList.sortedByDescending { it.title }  // Assume 'title' is a property in CardInfo
        adapter.updateData(filteredDataList)
    }
}
