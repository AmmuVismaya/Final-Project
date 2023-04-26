package com.example.barterbay.register

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.rpc.context.AttributeContext

class RegisterViewModel : ViewModel() {
   private lateinit var auth: FirebaseAuth
    var database = FirebaseDatabase.getInstance()
   private  var reference: DatabaseReference?= null
    var observableFirstName = MutableLiveData<String>()
    var observableLastName = MutableLiveData<String>()
    var observableEmail = MutableLiveData<String>()
    var observablePassword = MutableLiveData<String>()
    var observableConfirmPassword = MutableLiveData<String>()
    var observableIsTermAndConditions = MutableLiveData<String>()
   val clickOption = MutableLiveData<Int>()
   fun onClick(s:Int) {
       clickOption.value = s
   }
    init {
        observableFirstName.value = ""
        observableLastName.value = ""
        observablePassword.value = ""
        observableEmail.value = ""
        observableConfirmPassword.value = ""
    }

    fun registerUser() {
        var fName = observableFirstName.value.toString()
        var lName = observableLastName.value.toString()
        var password = observablePassword.value.toString()
        var email = observableEmail.value.toString()
        var coform = observableConfirmPassword.toString()




       auth = FirebaseAuth.getInstance()
       auth?.let { login ->
          login.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener { task: Task<AuthResult> ->

                if (!task.isSuccessful) {
                   println("Login Failed with ${task.exception}")

                } else {
                  callRegiser(fName,lName,email)

                }

             }

       }
    }

   private fun callRegiser(fName: String, lName: String, email: String) {

       val user: FirebaseUser? = auth.currentUser
       val userId: String = user!!.uid
       val root = database.getReference("users").child(userId)


       val userData = HashMap<String,String>()
       userData.put("FirstName",fName)
       userData.put("LastName",lName)
       userData.put("Email",email)
       userData.put("ProfileImage","")

       if (user != null) {
           root.setValue(userData)
               .addOnSuccessListener {
                   // Data successfully saved
                   clickOption.value = 1
               }
               .addOnFailureListener {
                   // Error occurred while saving the data
                   Log.d("Error", "onCancelled:${it.message} ")
               }
       }
   }
    fun getClicked():MutableLiveData<Int> = clickOption

}
