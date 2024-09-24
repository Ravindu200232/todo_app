package com.mbexample.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mbexample.alarmmanager.R

class UpdateCard : AppCompatActivity() {
    private lateinit var updateTitle: EditText
    private lateinit var priorityRadioGroup: RadioGroup
    private lateinit var selectedDate: TextView
    private lateinit var selectedTime: TextView
    private lateinit var deleteButton: Button
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)

        updateTitle = findViewById(R.id.update_title)
        priorityRadioGroup = findViewById(R.id.priority_radio_group)
        selectedDate = findViewById(R.id.selected_date)
        selectedTime = findViewById(R.id.selected_time)
        deleteButton = findViewById(R.id.delete_button)
        updateButton = findViewById(R.id.update_button)

        val pos = intent.getIntExtra("position", -1)
        if (pos != -1) {
            val card = SharedPreferencesManager.loadCards(this)[pos]
            updateTitle.setText(card.title)
            setSelectedPriority(card.priority)
            selectedDate.text = card.Rdate
            selectedTime.text = card.Rtime

            deleteButton.setOnClickListener {
                val cards = SharedPreferencesManager.loadCards(this).toMutableList()
                cards.removeAt(pos)
                SharedPreferencesManager.saveCards(cards, this)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            updateButton.setOnClickListener {
                val cards = SharedPreferencesManager.loadCards(this).toMutableList()
                cards[pos] = CardInfo(
                    updateTitle.text.toString(),
                    getSelectedPriority(),
                    selectedDate.text.toString(),
                    selectedTime.text.toString()
                )
                SharedPreferencesManager.saveCards(cards, this)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
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

    private fun setSelectedPriority(priority: String) {
        when (priority) {
            "High" -> priorityRadioGroup.check(R.id.radio_high)
            "Medium" -> priorityRadioGroup.check(R.id.radio_medium)
            "Low" -> priorityRadioGroup.check(R.id.radio_low)
        }
    }
}
