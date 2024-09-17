package com.mbexample.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbexample.alarmmanager.R

class Adapter(private var data: List<CardInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val layout: LinearLayout = itemView.findViewById(R.id.mylayout)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
        val completeButton: Button = itemView.findViewById(R.id.complete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        // Set the background color based on priority
        when (item.priority.toLowerCase()) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#32CD32"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#EDC988"))
        }

        // Set the text for each view
        holder.title.text = item.title
        holder.priority.text = item.priority
        holder.date.text = item.Rdate
        holder.time.text = item.Rtime

        // Handle click events for the item view
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }

        // Handle click events for the complete button
        holder.completeButton.setOnClickListener {
            // Mark the task as complete by blurring or crossing out
            item.isCompleted = true
            notifyItemChanged(position)
        }

        // Update the view to reflect completion
        if (item.isCompleted) {
            holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.layout.setBackgroundColor(Color.parseColor("#D3D3D3")) // Light grey to indicate completion
            holder.completeButton.visibility = View.GONE
        } else {
            holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.layout.setBackgroundColor(when (item.priority.toLowerCase()) {
                "high" -> Color.parseColor("#F05454")
                "medium" -> Color.parseColor("#32CD32")
                else -> Color.parseColor("#EDC988")
            })
            holder.completeButton.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<CardInfo>) {
        data = newData
        notifyDataSetChanged()
    }
}
