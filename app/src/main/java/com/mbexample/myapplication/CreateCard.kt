package com.mbexample.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.mbexample.alarmmanager.R


class CreateCard : AppCompatActivity() {
    private lateinit var createTitle: EditText
    private lateinit var priorityRadioGroup: RadioGroup
    private lateinit var selectedDate: TextView
    private lateinit var selectedTime: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        createTitle = findViewById(R.id.create_title)
        priorityRadioGroup = findViewById(R.id.priority_radio_group)
        selectedDate = findViewById(R.id.selected_date)
        selectedTime = findViewById(R.id.selected_time)

        findViewById<Button>(R.id.save_button).setOnClickListener {
            val cards = SharedPreferencesManager.loadCards(this).toMutableList()
            cards.add(
                CardInfo(
                    createTitle.text.toString(),
                    getSelectedPriority(),
                    selectedDate.text.toString(),
                    selectedTime.text.toString()
                )
            )
            SharedPreferencesManager.saveCards(cards, this)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getSelectedPriority(): String {
        return when (priorityRadioGroup.checkedRadioButtonId) {
            R.id.radio_high -> "High"
            R.id.radio_medium -> "Medium"
            R.id.radio_low -> "Low"
            else -> "Medium"
        }
    }
}























