package edu.put.inf151867

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

fun AppCompatActivity.registerForResult(onResult: (resultCode: Int,data: Intent?) -> Unit):
        ActivityResultLauncher<Intent> {
    return this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> onResult(result.resultCode, result.data)
    }
}

fun toTwoDigits (value: String): String {
    return if (value.toInt() < 10) "0$value" else "$value"
}

fun getEaster(passedYear: Int): Array<Date> {
    val year = passedYear + 1900
    val a: Int = year % 19
    val b: Int = year / 100
    val c: Int = year % 100
    val d: Int = b / 4
    val e: Int = b % 4
    val f: Int = (b + 8) / 25
    val g: Int = (b - f + 1) / 3
    val h: Int = ((19 * a) + b - d - g + 15) % 30
    val i: Int = c / 4
    val k: Int = c % 4
    val l: Int = (32 + 2 * e + 2 * i - h - k) % 7
    val m: Int = ((a + 11 * h + 22 * l) / 451)
    val p: Int = (h + l - 7 * m + 114) % 31
    val day = p + 1
    val month = (h + l - 7 * m + 114) / 31
    val easter = Date(year-1900,month-1,day)
    val easterCalendar = Calendar.getInstance()
    easterCalendar.time = easter
    easterCalendar.add(Calendar.DATE,1)
    val easterMonday = easterCalendar.time
    easterCalendar.add(Calendar.DATE,59)
    val easterThursday = easterCalendar.time
    return arrayOf(easterMonday, easterThursday)
}