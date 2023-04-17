package edu.put.inf151867

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.put.inf151867.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    private var h1: Int = 0
    private var m1: Int = 0
    private var s1: Int = 0
    private var h2: Int = 0
    private var m2: Int = 0
    private var s2: Int = 0
    private val myActionResult = registerForResult { resultCode, data ->
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (data.hasExtra("resultArrayTime")) {
                    h1 = data.extras?.getIntArray("resultArrayTime")?.get(0) ?: 0
                    m1 = data.extras?.getIntArray("resultArrayTime")?.get(1) ?: 0
                    s1 = data.extras?.getIntArray("resultArrayTime")?.get(2) ?: 0
                    h2 = data.extras?.getIntArray("resultArrayTime")?.get(3) ?: 0
                    m2 = data.extras?.getIntArray("resultArrayTime")?.get(4) ?: 0
                    s2 = data.extras?.getIntArray("resultArrayTime")?.get(5) ?: 0
                }
            }
        }
    }

    fun runDateActivity(v: View) {
        val i = Intent(this, DateActivity::class.java)
        startActivity(i)
    }

    fun runTimeActivity(v: View) {
        val valueArray = intArrayOf(h1,m1,s1,h2,m2,s2)
        val i = Intent(this, TimeActivity::class.java)
        i.putExtra("initArray", valueArray)
        myActionResult.launch(i)
    }
}