package com.mbexample.myapplication

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbexample.alarmmanager.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val priority: TextView = itemView.findViewById(R.id.priority)
    val layout: LinearLayout = itemView.findViewById(R.id.mylayout)
    val date: TextView = itemView.findViewById(R.id.date)
    val time: TextView = itemView.findViewById(R.id.time)

}
