package com.manasomali.timelinelist.activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.R
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*


class CadastroActivity : AppCompatActivity() {

    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        imageview_voltar_cadastrotologin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        button_cadastro_cadastrese.setOnClickListener {
            if((edittext_cadastro_nome.text.toString().isNotBlank())&&(edittext_cadastro_sobrenome.text.toString().isNotBlank())&&(edittext_cadastro_email.text.toString().isNotBlank())&&(edittext_cadastro_senha1.text.toString().isNotBlank())) {
                if(edittext_cadastro_senha2.text.toString()==edittext_cadastro_senha1.text.toString()) {
                    cadastroUsuarioFirebase(edittext_cadastro_email.text.toString(),
                        edittext_cadastro_senha1.text.toString())
                    startLoading()
                } else {
                    Toast.makeText(this, "Senhas não são iguais.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Informe os dados.", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun cadastroUsuarioFirebase(email: String, senha: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(
            this) { task ->
            if(task.isSuccessful) {
                setDisplayName()
                val emailUser = task.result?.user!!.email
                Toast.makeText(this, "Usuário cadastrado.", Toast.LENGTH_LONG).show()
                sharedPrefs.edit().putString(Constants.KEY_IDUSER, task.result?.user!!.uid).apply()
                var nomes = task.result?.user!!.displayName?.split(" ")?.map { it.trim() }
                sharedPrefs.edit().putString(Constants.KEY_NOME, nomes?.get(0)).apply()
                sharedPrefs.edit().putString(Constants.KEY_SOBRENOME, nomes?.get(1)).apply()
                sharedPrefs.edit().putString(Constants.KEY_EMAIL, emailUser).apply()
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
            endLoading()
        }
    }
    fun setDisplayName() {
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(edittext_cadastro_nome.text.toString()+" "+edittext_cadastro_sobrenome.text.toString()).build()
        user!!.updateProfile(profileUpdates)
    }
    fun startLoading() {
        Toast.makeText(this, "Aguarde...", Toast.LENGTH_SHORT).show()
        button_cadastro_cadastrese.isClickable = false
        edittext_cadastro_nome.isFocusable = false
        edittext_cadastro_email.isFocusable = false
        edittext_cadastro_senha1.isFocusable = false
        edittext_cadastro_senha2.isFocusable = false
        progressbar_loading_login.visibility = View.VISIBLE
        progressbar_loading_login.background.alpha = 150
    }
    fun endLoading() {
        button_cadastro_cadastrese.isClickable = true
        edittext_cadastro_nome.isEnabled = true
        edittext_cadastro_email.isEnabled = true
        edittext_cadastro_senha1.isEnabled = true
        edittext_cadastro_senha2.isEnabled = true
        progressbar_loading_login.visibility = View.GONE
    }
}