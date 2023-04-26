package com.example.barterbay.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.barterbay.R
import com.example.barterbay.dashboard.DashBoardActivity
import com.example.barterbay.databinding.ActivityLoginBinding
import com.example.barterbay.register.RegisterActivty

class LoginActivity : AppCompatActivity() {
    lateinit var  binding:ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
       viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getClicked().observe(this){
            startActivity(Intent(this,DashBoardActivity::class.java))
        }
    }

}