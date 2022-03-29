package com.lzy.dashboardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import com.lzy.dashboardview.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        frameLayout = findViewById(R.id.container)
        Log.d("MainActivity", "frameLayout = $frameLayout")
        Toast.makeText(this, "main", Toast.LENGTH_LONG).show()
    }
}