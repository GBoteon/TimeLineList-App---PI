package com.manasomali.timelinelist.activities

import android.R.attr
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.R
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    companion object {
        const val RC_SIGN_IN = 120
    }

    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        button_login.setOnClickListener {
            if((edittext_login_email.text.toString().isNotBlank())&&(edittext_login_senha.text.toString().isNotBlank())) {
                singinUsuarioFirebase(edittext_login_email.text.toString(), edittext_login_senha.text.toString())
                startLoading()
            } else {
                Toast.makeText(this, "Informe o email e a senha.", Toast.LENGTH_LONG).show()
            }
        }
        button_cadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
        siginbutton_google.setOnClickListener {
            signinFirebaseGoogle()
            startLoading()
        }


    }
    fun singinUsuarioFirebase(email: String, senha: String) {
        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(
            this) { task ->
            if(task.isSuccessful) {
                sharedPrefs.edit().putString(Constants.KEY_IDUSER, task.result?.user!!.uid).apply()
                sharedPrefs.edit().putString(Constants.KEY_EMAIL, task.result?.user!!.email).apply()
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                println("erro: ${task.exception?.message.toString()}")
            }
        }
    }
    private fun signinFirebaseGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                println("firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Login falhou.", Toast.LENGTH_SHORT).show()
                endLoading()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser
                    sharedPrefs.edit().putString(Constants.KEY_IDUSER, user?.uid).apply()
                    var nomes = task.result?.user!!.displayName?.split(" ")?.map { it.trim() }
                    sharedPrefs.edit().putString(Constants.KEY_NOME, nomes?.get(0)).apply()
                    sharedPrefs.edit().putString(Constants.KEY_EMAIL, nomes?.get(1)).apply()
                    sharedPrefs.edit().putString(Constants.KEY_FOTO, user?.photoUrl.toString()).apply()
                    endLoading()
                    startActivity(Intent(this, ListaActivity::class.java))
                } else {
                    Toast.makeText(this, "Login falhou.", Toast.LENGTH_SHORT).show()
                    endLoading()
                }
            }
    }
    fun startLoading() {
        Toast.makeText(this, "Aguarde...", Toast.LENGTH_SHORT).show()
        button_login.isClickable = false
        button_cadastro.isClickable = false
        siginbutton_google.isClickable = false
        siginbutton_facebook.isClickable = false
        edittext_login_email.isEnabled = false
        edittext_login_senha.isEnabled = false
        progressbar_loading_login.visibility = VISIBLE
        progressbar_loading_login.background.alpha = 150
    }
    fun endLoading() {
        siginbutton_google.isClickable = true
        button_cadastro.isClickable = true
        siginbutton_google.isClickable = true
        siginbutton_facebook.isClickable = true
        edittext_login_email.isEnabled = true
        edittext_login_senha.isEnabled = true
        progressbar_loading_login.visibility = GONE
    }

}