package com.example.barterbay.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel:ViewModel() {
    private lateinit var auth: FirebaseAuth

    var observablePassword = MutableLiveData<String>()
    var observableEmail = MutableLiveData<String>()
    val clickoptopn = MutableLiveData<Int>()
   fun onClick(s:Int) {
       clickoptopn.value = s
   }

    init {

        observableEmail.value="Siril@gmail.com"
        observablePassword.value ="Siri@6943"
    }

    fun loginRegisteredUser() {
        auth = FirebaseAuth.getInstance()
        var email = observableEmail.value.toString()
        var password = observablePassword.value.toString()


        auth?.let { login ->
            login.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->

                    if (!task.isSuccessful) {
                        println("Login Failed with ${task.exception}")

                    } else {
                        clickoptopn.value = 1

                    }

                }

        }
    }
    fun getClicked():MutableLiveData<Int> = clickoptopn
}