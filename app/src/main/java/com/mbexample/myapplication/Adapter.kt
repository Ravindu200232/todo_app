package com.mbexample.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbexample.alarmmanager.R

class Adapter(private var cardList: List<CardInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_create_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cardList[position]
        holder.title.text = card.title
        holder.priority.text = card.priority
        holder.date.text = card.Rdate
        holder.time.text = card.Rtime

        holder.itemView.setOnClickListener {
            // Handle item click to update
            val intent = Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("position", position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = cardList.size

    fun updateData(newCardList: List<CardInfo>) {
        cardList = newCardList
        notifyDataSetChanged()
    }
}
