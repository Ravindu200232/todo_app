package com.mbexample.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbexample.alarmmanager.R

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var deleteAllButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var cardList: List<CardInfo> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.add)
        deleteAllButton = findViewById(R.id.deleteAll)
        recyclerView = findViewById(R.id.recycler_view)

        cardList = SharedPreferencesManager.loadCards(this)

        adapter = Adapter(cardList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            startActivity(Intent(this, CreateCard::class.java))
        }

        deleteAllButton.setOnClickListener {
            SharedPreferencesManager.clearAll(this)
            cardList = SharedPreferencesManager.loadCards(this) // Refresh the list
            adapter.updateData(cardList)
        }
    }
}
