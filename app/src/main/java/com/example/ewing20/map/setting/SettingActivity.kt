package com.example.ewing20.map.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toolbar
import com.example.ewing20.R

class SettingActivity : AppCompatActivity() {

    private lateinit var unitRadioGroup: RadioGroup
    private lateinit var distanceSeekBar: SeekBar
    private lateinit var distanceProgressTextView: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var isMetric = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        unitRadioGroup = findViewById(R.id.unitRadioGroup)
        distanceSeekBar = findViewById(R.id.distanceSeekBar)
        distanceProgressTextView = findViewById(R.id.distanceProgressTextView)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        isMetric = sharedPreferences.getBoolean("isMetric", true)

        // set the initial unit system
        if (isMetric) {
            findViewById<RadioButton>(R.id.metricRadioButton).isChecked = true
        } else {
            findViewById<RadioButton>(R.id.imperialRadioButton).isChecked = true
        }

        // set the initial maximum distance
        val maxDistance = sharedPreferences.getInt("maxDistance", 50)
        distanceSeekBar.progress = maxDistance
        updateDistanceText(maxDistance)

        // listen for changes to the unit system
        unitRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            isMetric = checkedId == R.id.metricRadioButton
            with(sharedPreferences.edit()) {
                putBoolean("isMetric", isMetric)
                apply()
            }
            updateDistanceText(distanceSeekBar.progress)
        }

        // listen for changes to the maximum distance
        distanceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateDistanceText(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        with(sharedPreferences.edit()) {
            putInt("maxDistance", distanceSeekBar.progress)
            apply()
        }
        super.onBackPressed()
    }

    private fun updateDistanceText(progress: Int) {
        val distance = if (isMetric) {
            "$progress km"
        } else {
            String.format("%.0f mi", progress * 0.621371)
        }
        distanceProgressTextView.text = distance
    }
}