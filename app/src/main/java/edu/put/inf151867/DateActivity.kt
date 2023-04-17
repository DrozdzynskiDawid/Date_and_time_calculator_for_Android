package edu.put.inf151867

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import edu.put.inf151867.databinding.ActivityDateBinding
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

class DateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setDaysValues(0,0)

        var upperDate = Date(
            binding.datePickerUp.year - 1900,
            binding.datePickerUp.month,
            binding.datePickerUp.dayOfMonth
        )
        var lowerDate = Date(
            binding.datePickerBottom.year - 1900,
            binding.datePickerBottom.month,
            binding.datePickerBottom.dayOfMonth
        )
        binding.datePickerUp.setOnDateChangedListener() {
                view, year, monthOfYear, dayOfMonth ->
            upperDate = Date(year - 1900,monthOfYear,dayOfMonth)
            val days = calculateDayDiff(upperDate, lowerDate)
            val workingDays = calculateWorkingDays(days, upperDate, lowerDate)
            setDaysValues(days, workingDays)
        }
        binding.datePickerBottom.setOnDateChangedListener() {
                view, year, monthOfYear, dayOfMonth ->
            lowerDate = Date(year - 1900,monthOfYear,dayOfMonth)
            val days = calculateDayDiff(upperDate, lowerDate)
            val workingDays = calculateWorkingDays(days, upperDate, lowerDate)
            setDaysValues(days, workingDays)
        }
        binding.daysDiff.doOnTextChanged() {
                text, start, before, count ->
            val number = binding.daysDiff.text.toString().toIntOrNull()
            if (number != null) {
                if (number >= 0) {
                    binding.addDays.text = getString(R.string.button3)
                }
                else {
                    binding.addDays.text = getString(R.string.button4)
                }
            }
        }
    }

    fun onAddDaysClicked (v: View) {
        val numberOfDays = binding.daysDiff.text.toString().toInt()
        val upperDate = Date(
            binding.datePickerUp.year - 1900,
            binding.datePickerUp.month,
            binding.datePickerUp.dayOfMonth
        )
        val cal = Calendar.getInstance()
        cal.time = upperDate
        // wprowadzona ujemna liczba odejmie dni, dodatnia doda
        cal.add(Calendar.DATE, numberOfDays)
        binding.datePickerBottom.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
    }

    private fun calculateDayDiff(date1: Date, date2: Date): Int {
        if (date1 == date2) {
            return 0
        }
        val difference = if (date1.before(date2)) {
            (date2.time - date1.time).milliseconds
        } else {
            (date1.time - date2.time).milliseconds
        }
        return difference.inWholeDays.toInt()
    }

    private fun calculateWorkingDays(days: Int, date1: Date, date2: Date): Int {
        if (days == 0) {
            return 0
        }
        var workingDays = days
        var earlierDate: Date
        var laterDate: Date
        if (date1.before(date2)) {
            earlierDate = date1
            laterDate = date2
        }
        else {
            earlierDate = date2
            laterDate = date1
        }
        val holidays: MutableList<Date> = mutableListOf()
        for (i in earlierDate.year+1900..laterDate.year+1900) {
            holidays += getHolidays(i)
        }
        while (earlierDate.before(laterDate) || earlierDate == laterDate) {
            val calendar = Calendar.getInstance()
            calendar.time = earlierDate
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                workingDays -= 1
            }
            else {
                if (earlierDate in holidays) {
                    workingDays -= 1
                }
            }
            calendar.add(Calendar.DATE,1)
            earlierDate = calendar.time
            if (workingDays <= 0) {
                break
            }
        }
        return workingDays
    }

    private fun getHolidays(passedYear: Int): MutableList<Date> {
        val year = passedYear - 1900
        val holidaysList = mutableListOf(
            Date(year, 0,1),
            Date(year, 0,6),
            Date(year, 4,1),
            Date(year, 4,3),
            Date(year, 7,15),
            Date(year, 7,15),
            Date(year, 10,1),
            Date(year, 10,11),
            Date(year, 11,25),
            Date(year, 11,26)
        )
        val easterHolidayDays = getEaster(year)
        for (day in easterHolidayDays) {
            holidaysList.add(day)
        }
        return holidaysList
    }

    private fun setDaysValues (days: Int, workingDays: Int) {
        val text: String = getString(R.string.workingDaysText)
        binding.daysDiff.setText("$days")
        binding.workingDaysText.text = "$text $workingDays"
    }
}