package com.manasomali.timelinelist.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.Constants.KEY_THEME
import com.manasomali.timelinelist.Constants.PREFS_NAME
import com.manasomali.timelinelist.Constants.THEME_UNDEFINED
import com.manasomali.timelinelist.R
import kotlinx.coroutines.*


class LoadingActivity : AppCompatActivity() {

    val sharedPrefs by lazy {  getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        when (getSavedTheme()) {
            0 -> setTema(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> setTema(AppCompatDelegate.MODE_NIGHT_YES)
            -1 -> setTema(AppCompatDelegate.MODE_NIGHT_NO)
        }

        var intent = Intent(this, LoginActivity::class.java)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            sharedPrefs.edit().putString(Constants.KEY_IDUSER, user.uid).apply()
            for (user in FirebaseAuth.getInstance().currentUser!!.providerData) {
                if (user.providerId == "password") {
                    var nomes = user.displayName.toString().split(" ").map { it.trim() }
                    sharedPrefs.edit().putString(Constants.KEY_NOME, nomes[0]).apply()
                    sharedPrefs.edit().putString(Constants.KEY_SOBRENOME, nomes[1]).apply()
                    sharedPrefs.edit().putString(Constants.KEY_EMAIL, user.email).apply()
                    sharedPrefs.edit().putString(Constants.KEY_FOTO, user.photoUrl.toString()).apply()
                    Toast.makeText(this, "Sign In usando email e password", Toast.LENGTH_SHORT).show()

                }
                if (user.providerId == "facebook.com") {
                    println("User is signed in with Facebook")
                }
                if (user.providerId == "google.com") {
                    var nomes = user.displayName.toString().split(" ").map { it.trim() }
                    sharedPrefs.edit().putString(Constants.KEY_NOME, nomes[0]).apply()
                    sharedPrefs.edit().putString(Constants.KEY_SOBRENOME, nomes[1]).apply()
                    sharedPrefs.edit().putString(Constants.KEY_EMAIL, user.email).apply()
                    sharedPrefs.edit().putString(Constants.KEY_FOTO, user.photoUrl.toString()).apply()
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
    private fun getSavedTheme() = sharedPrefs.getInt(KEY_THEME, THEME_UNDEFINED)
    private fun setTema(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }
}