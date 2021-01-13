package com.manasomali.timelinelist.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.R
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        button_login.setOnClickListener {
            if((edittext_login_email.text.toString().isNotBlank())&&(edittext_login_senha.text.toString().isNotBlank())) {
                singinUsuarioFirebase(edittext_login_email.text.toString(), edittext_login_senha.text.toString())
            } else {
                Toast.makeText(this, "Informe o email e a senha.", Toast.LENGTH_LONG).show()
            }
        }
        button_cadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
    fun singinUsuarioFirebase(email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                sharedPrefs.edit().putString(Constants.KEY_IDUSER, task.result?.user!!.uid).apply()
                sharedPrefs.edit().putString(Constants.KEY_EMAIL, task.result?.user!!.email).apply()
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                println(task.exception?.message.toString())
            }
        }
    }
}