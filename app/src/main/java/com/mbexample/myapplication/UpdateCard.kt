package com.mbexample.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mbexample.alarmmanager.R
import java.util.Calendar

class UpdateCard : AppCompatActivity() {
    private lateinit var updateTitle: EditText
    private lateinit var priorityRadioGroup: RadioGroup
    private lateinit var selectedDate: TextView
    private lateinit var selectedTime: TextView
    private lateinit var deleteButton: Button
    private lateinit var updateButton: Button
    private lateinit var selectDateButton: Button
    private lateinit var selectTimeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_card)

        // Initialize views
        updateTitle = findViewById(R.id.update_title)
        priorityRadioGroup = findViewById(R.id.priority_radio_group) // Use RadioGroup
        selectedDate = findViewById(R.id.selected_date)
        selectedTime = findViewById(R.id.selected_time)
        deleteButton = findViewById(R.id.delete_button)
        updateButton = findViewById(R.id.update_button)
        selectDateButton = findViewById(R.id.select_date_button)
        selectTimeButton = findViewById(R.id.select_time_button)

        // Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val card = DataObject.getData(pos)
            updateTitle.setText(card.title)
            setSelectedPriority(card.priority) // Set selected priority based on existing data
            selectedDate.text = card.Rdate
            selectedTime.text = card.Rtime

            deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                myIntent()
            }

            updateButton.setOnClickListener {
                val selectedPriority = getSelectedPriority() // Get selected priority
                DataObject.updateData(
                    pos,
                    updateTitle.text.toString(),
                    selectedPriority,
                    selectedDate.text.toString(),
                    selectedTime.text.toString()
                )
                myIntent()
            }
        }

        selectDateButton.setOnClickListener { showDatePicker() }
        selectTimeButton.setOnClickListener { showTimePicker() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.text = "$dayOfMonth/${month + 1}/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime.text = String.format("%02d:%02d", hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    private fun getSelectedPriority(): String {
        return when (priorityRadioGroup.checkedRadioButtonId) {
            R.id.radio_high -> "High"
            R.id.radio_medium -> "Medium"
            R.id.radio_low -> "Low"
            else -> "Medium" // Default value if none selected
        }
    }

    private fun setSelectedPriority(priority: String) {
        when (priority) {
            "High" -> priorityRadioGroup.check(R.id.radio_high)
            "Medium" -> priorityRadioGroup.check(R.id.radio_medium)
            "Low" -> priorityRadioGroup.check(R.id.radio_low)
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
