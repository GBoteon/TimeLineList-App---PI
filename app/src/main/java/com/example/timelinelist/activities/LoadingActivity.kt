package com.example.timelinelist.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.coroutines.*

class LoadingActivity : AppCompatActivity() {

    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {

        var sharedPref = getSharedPreferences("save", Context.MODE_PRIVATE)
        var theme = sharedPref.getBoolean("value",false)
        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        var intent = Intent(this, LoginActivity::class.java)
        scope.launch {
            delay(1000)
            startActivity(intent)
            finish()
        }
    }
    override fun onPause() {
        scope.cancel()
        super.onPause()
    }
}