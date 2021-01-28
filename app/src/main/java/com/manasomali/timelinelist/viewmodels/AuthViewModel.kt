package com.manasomali.timelinelist.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel(): ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var stateRegister: MutableLiveData<Boolean> = MutableLiveData()
    var stateLogin: MutableLiveData<Boolean> = MutableLiveData()
    var stateGoogleLogin: MutableLiveData<Boolean> = MutableLiveData()
    var stateFaceLogin: MutableLiveData<Boolean> = MutableLiveData()
    val db = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()

    fun cadastroUsuarioFirebase(email: String, senha: String, nome:String, sobrenome:String) {
        loading.value = true
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        addUser(task.result.user?.uid.toString(), nome, sobrenome, email, "")
                    }
                    else -> {
                        Log.e("cadastroUsuarioFirebase",task.exception?.message.toString())
                        stateRegister.value = false
                        loading.value = false
                    }
                }
            }
    }

    fun loginUsuarioFirebase(email: String, senha: String) {
        loading.value = true
        firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                when {
                    task.isSuccessful -> {
                        Log.d("loginUsuarioFirebase", "Usuário logado com sucesso.")
                    }
                    else -> {
                        Log.e("loginUsuarioFirebase",task.exception?.message.toString())
                        stateLogin.value = false
                        loading.value = false
                    }
                }
            }
    }
    fun handleGoogleAccessToken(idToken: String) {
        loading.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("handleGoogle", "Usuário logado com google com sucesso.")
                    val user = firebaseAuth.currentUser
                    var nomes = user?.displayName.toString().split(" ").map { it.trim() }
                    addUser(user?.uid.toString(), nomes[0], nomes[1], user?.email.toString(), user?.photoUrl.toString())
                } else {
                    Log.e("handleGoogle",task.exception?.message.toString())
                    stateGoogleLogin.value = false
                    loading.value = false
                }
            }
    }
    fun handleFacebookAccessToken(token: AccessToken) {
        loading.value = true
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("handleFacebook", "Usuário logado com facebook com sucesso.")
                    val user = firebaseAuth.currentUser
                    var nomes = user?.displayName.toString().split(" ").map { it.trim() }
                    addUser(user?.uid.toString(), nomes[0], nomes[1], user?.email.toString(), user?.photoUrl.toString())
                } else {
                    Log.e("handleFacebook",task.exception?.message.toString())
                    stateFaceLogin.value = false
                    loading.value = false
                }
            }
    }
    fun addUser(uid:String, nome:String, sobrenome:String, email:String, foto:String) {
        var docRef = db.collection("users").document(uid)
        val user = hashMapOf(
            "nome" to nome,
            "sobrenome" to sobrenome,
            "email" to email,
            "foto" to foto,
            "uid" to uid
        )
        docRef.set(user)
            .addOnSuccessListener {
                stateLogin.value = true
                Log.d("addUser", "Usuário adicionado com sucesso.")
            }
            .addOnFailureListener { e ->
                stateLogin.value = false
                Log.w("addUser", "Erro ao adicionar usuário.", e)
            }
            .addOnCompleteListener {
                stateLogin.value = false
            }
    }
}