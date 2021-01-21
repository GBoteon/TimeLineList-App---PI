package com.manasomali.timelinelist.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.R
import com.manasomali.timelinelist.UserSetup
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    companion object {
        const val RC_SIGN_IN = 120
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

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
                singinFirebaseEmailSenha(edittext_login_email.text.toString(),
                    edittext_login_senha.text.toString())
                    startLoading()
            } else {
                Toast.makeText(this, "Informe o email e a senha.", Toast.LENGTH_LONG).show()
            }
        }
        button_cadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
        val user = Firebase.auth.currentUser
        siginbutton_google.setOnClickListener {
            if (user == null) {
                signinFirebaseGoogle()
                startLoading()
            }
        }

        siginbutton_facebook.setOnClickListener {
            if (user == null) {
                signinFirebaseFacebook()
                startLoading()
            }
        }
    }

    fun singinFirebaseEmailSenha(email: String, senha: String) {
        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(
            this) { task ->
            if(task.isSuccessful) {
                val user = firebaseAuth.currentUser
                UserSetup.saveUser(this, user!!)
                endLoading()
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                endLoading()
            }
        }
    }
    private fun signinFirebaseGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun signinFirebaseFacebook() {
        callbackManager = CallbackManager.Factory.create()
        siginbutton_facebook.setPermissions("email", "user_photos", "public_profile")
        siginbutton_facebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }
            override fun onCancel() {
                println("facebook:onCancel")
                Toast.makeText(this@LoginActivity, "Login cancelado.", Toast.LENGTH_SHORT).show()
            }
            override fun onError(error: FacebookException) {
                println("facebook:onError $error")
                Toast.makeText(this@LoginActivity, "Login falhou: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != RC_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                println("firebaseAuthWithGoogle:" + account.id)
                handleGoogleAccessToken(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Login falhou.", Toast.LENGTH_SHORT).show()
                endLoading()
            }
        }
    }
    private fun handleGoogleAccessToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser
                    UserSetup.saveUser(this, user!!)
                    endLoading()
                    startActivity(Intent(this, ListaActivity::class.java))
                } else {
                    Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    endLoading()
                }
            }
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    println("signInWithCredential:success")
                    Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser
                    UserSetup.saveUser(this, user!!)
                    endLoading()
                    startActivity(Intent(this, ListaActivity::class.java))
                } else {
                    Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
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