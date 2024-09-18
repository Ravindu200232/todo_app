package com.mbexample.myapplication

import android.webkit.WebSettings.RenderPriority
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "To_Do")

data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority:String,
    var Rdate:String,
    var Rtime:String,
    var isCompleted: Boolean = false

)