package edu.put.inf151867

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.put.inf151867.databinding.ActivityTimeBinding
import java.sql.Time

class TimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val extras = intent.extras ?: return
        val initValues = extras.getIntArray("initArray")
        if (initValues != null) {
            binding.h1.setText(toTwoDigits(initValues[0].toString()))
            binding.m1.setText(toTwoDigits(initValues[1].toString()))
            binding.s1.setText(toTwoDigits(initValues[2].toString()))
            binding.h2.setText(toTwoDigits(initValues[3].toString()))
            binding.m2.setText(toTwoDigits(initValues[4].toString()))
            binding.s2.setText(toTwoDigits(initValues[5].toString()))
        }
    }

    fun onAddClick (v: View) {
        calculateTime("add")
    }

    fun onSubtractClick (v: View) {
        calculateTime("subtract")
    }

    fun onACClick (v: View) {
        clearTimeFields("all")
    }

    private fun calculateTime (operation: String) {
        val upperTime = Time(
            binding.h1.text.toString().toInt(),
            binding.m1.text.toString().toInt(),
            binding.s1.text.toString().toInt()
        )
        val lowerTime = Time(
            binding.h2.text.toString().toInt(),
            binding.m2.text.toString().toInt(),
            binding.s2.text.toString().toInt()
        )
        var result = Time(0)
        if (operation == "add") {
            result = Time(upperTime.time + lowerTime.time - upperTime.timezoneOffset * 60000)
        }
        if (operation == "subtract") {
            result = Time(upperTime.time - lowerTime.time + upperTime.timezoneOffset * 60000)
        }
        val resultHour = result.hours
        binding.h1.setText(toTwoDigits(resultHour.toString()))
        val resultMinute = result.minutes
        binding.m1.setText(toTwoDigits(resultMinute.toString()))
        val resultSecond = result.seconds
        binding.s1.setText(toTwoDigits(resultSecond.toString()))
        clearTimeFields("bottom")
    }

    private fun clearTimeFields (type: String) {
        val clearedValue = getString(R.string.clearedValue)
        if (type == "bottom") {
            binding.h2.setText(clearedValue)
            binding.m2.setText(clearedValue)
            binding.s2.setText(clearedValue)
        }
        if (type == "all") {
            binding.h1.setText(clearedValue)
            binding.h2.setText(clearedValue)
            binding.m1.setText(clearedValue)
            binding.m2.setText(clearedValue)
            binding.s1.setText(clearedValue)
            binding.s2.setText(clearedValue)
        }
    }

    override fun finish() {
        val data = Intent()
        data.putExtra("resultArrayTime", intArrayOf(
            binding.h1.text.toString().toInt(),
            binding.m1.text.toString().toInt(),
            binding.s1.text.toString().toInt(),
            binding.h2.text.toString().toInt(),
            binding.m2.text.toString().toInt(),
            binding.s2.text.toString().toInt(),
            )
        )
        setResult(Activity.RESULT_OK,data)
        super.finish()
    }
}