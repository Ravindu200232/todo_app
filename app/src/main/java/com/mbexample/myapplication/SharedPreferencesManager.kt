package com.mbexample.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

object SharedPreferencesManager {
    private const val PREFS_NAME = "card_info_prefs"
    private const val KEY_CARDS = "cards"

    fun saveCards(cards: List<CardInfo>, context: Context) {
        val cardStrings = cards.joinToString(";") { "${it.title},${it.priority},${it.Rdate},${it.Rtime}" }
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CARDS, cardStrings)
            .apply()
    }

    @SuppressLint("LongLogTag")
    fun loadCards(context: Context): List<CardInfo> {
        val cardString = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CARDS, null) ?: return emptyList()

        return cardString.split(";").mapNotNull {
            val parts = it.split(",")
            if (parts.size == 4) {
                CardInfo(parts[0], parts[1], parts[2], parts[3]) // Adjust based on CardInfo constructor
            } else {
                Log.e("SharedPreferencesManager", "Invalid CardInfo format: $it")
                null
            }
        }
    }

    fun clearAll(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_CARDS)
            .apply()
    }
}
