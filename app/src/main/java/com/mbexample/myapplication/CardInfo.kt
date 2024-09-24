package com.mbexample.myapplication

data class CardInfo(
    var title: String,
    var priority: String,
    var Rdate: String,
    var Rtime: String,
    var isCompleted: Boolean = false
)
