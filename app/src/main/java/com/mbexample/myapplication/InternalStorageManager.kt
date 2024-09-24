package com.mbexample.myapplication

import android.content.Context
import java.io.File

object InternalStorageManager {

    private const val FILE_NAME = "cards.txt"

    fun saveCards(cards: List<CardInfo>, context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        file.printWriter().use { out ->
            cards.forEach { card ->
                out.println("${card.title},${card.priority},${card.Rdate},${card.Rtime}")
            }
        }
    }

    fun loadCards(context: Context): List<CardInfo> {
        val file = File(context.filesDir, FILE_NAME)
        return if (file.exists()) {
            file.readLines().map { line ->
                val parts = line.split(",")
                CardInfo(
                    title = parts[0],
                    priority = parts[1],
                    Rdate = parts[2],
                    Rtime = parts[3]
                )
            }
        } else {
            emptyList()
        }
    }

    fun clearCards(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) {
            file.delete()
        }
    }
}
