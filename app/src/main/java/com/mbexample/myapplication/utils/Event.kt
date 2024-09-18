package com.mbexample.myapplication.utils


 class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? { // this function help to get the content
        return if (hasBeenHandled) {
           null
        } else {
            hasBeenHandled = true
            content
        }
    }

}

