package com.manasomali.timelinelist.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.manasomali.timelinelist.Constants.KEY_THEME
import com.manasomali.timelinelist.Constants.PREFS_NAME
import com.manasomali.timelinelist.Constants.THEME_UNDEFINED
import com.manasomali.timelinelist.R
import kotlinx.coroutines.*
import com.manasomali.timelinelist.viewmodels.AuthViewModel


class SplashActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    val sharedPrefs by lazy {  getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        when (sharedPrefs.getInt(KEY_THEME, THEME_UNDEFINED)) {
            0 -> setTema(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> setTema(AppCompatDelegate.MODE_NIGHT_YES)
            -1 -> setTema(AppCompatDelegate.MODE_NIGHT_NO)
        }

        var intent = Intent(this, LoginActivity::class.java)
        val user = Firebase.auth.currentUser
        if (user != null) {
            for (user in FirebaseAuth.getInstance().currentUser!!.providerData) {
                if (user.providerId == "password") {
                    Toast.makeText(this, "Sign In usando email e password", Toast.LENGTH_SHORT).show()
                }
                if (user.providerId == "facebook.com") {
                    Toast.makeText(this, "Sign In usando Facebook", Toast.LENGTH_SHORT).show()
                }
                if (user.providerId == "google.com") {
                    Toast.makeText(this, "Sign In usando Google", Toast.LENGTH_SHORT).show()

                }
            }
            intent = Intent(this, ListaActivity::class.java)
        }
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
    private fun setTema(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }


}