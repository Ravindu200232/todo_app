package com.mbexample.myapplication.alarm

import com.mbexample.myapplication.data.sources.local.Alarm

interface AlarmScheduler {

    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}