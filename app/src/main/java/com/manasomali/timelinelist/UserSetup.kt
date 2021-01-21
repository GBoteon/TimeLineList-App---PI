package com.manasomali.timelinelist

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.manasomali.timelinelist.Constants.APP_KEY
import com.manasomali.timelinelist.Constants.EMPTY_STRING
import com.manasomali.timelinelist.Constants.KEY_EMAIL
import com.manasomali.timelinelist.Constants.KEY_FOTO
import com.manasomali.timelinelist.Constants.KEY_IDUSER
import com.manasomali.timelinelist.Constants.KEY_NOME
import com.manasomali.timelinelist.Constants.KEY_SOBRENOME
import java.io.File

object UserSetup {
    lateinit var storageReference: StorageReference
    fun saveUser(context: Context, user: FirebaseUser) {
        val preferences = context.getSharedPreferences(APP_KEY, MODE_PRIVATE)
        var nomes = user.displayName.toString().split(" ").map { it.trim() }
        preferences.edit().putString(KEY_IDUSER, user.uid).apply()
        preferences.edit().putString(KEY_NOME, nomes[0]).apply()
        preferences.edit().putString(KEY_SOBRENOME, nomes[1]).apply()
        preferences.edit().putString(KEY_EMAIL, user.email).apply()
        storageReference = FirebaseStorage.getInstance().reference
        storageReference.child(user.uid + "/profilePicture/" + nomes[0]).downloadUrl.addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                preferences.edit().putString(KEY_FOTO, task.result.toString()).apply()
            } else {
                preferences.edit().putString(KEY_FOTO, user.photoUrl.toString()).apply()
            }
        }.addOnFailureListener {
            preferences.edit().putString(KEY_FOTO, user.photoUrl.toString()).apply()
        }.addOnCanceledListener {
            preferences.edit().putString(KEY_FOTO, user.photoUrl.toString()).apply()
        }
    }

    fun getUserNome(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_KEY, MODE_PRIVATE)
        return preferences.getString(KEY_NOME, EMPTY_STRING)
    }
    fun getUserSobrenome(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_KEY, MODE_PRIVATE)
        return preferences.getString(KEY_SOBRENOME, EMPTY_STRING)
    }
    fun getUserEmail(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_KEY, MODE_PRIVATE)
        return preferences.getString(KEY_EMAIL, EMPTY_STRING)
    }
    fun getUserFoto(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_KEY, MODE_PRIVATE)
        return preferences.getString(KEY_FOTO, EMPTY_STRING)
    }
    fun getUserId(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_KEY, MODE_PRIVATE)
        return preferences.getString(KEY_IDUSER, EMPTY_STRING)
    }
}