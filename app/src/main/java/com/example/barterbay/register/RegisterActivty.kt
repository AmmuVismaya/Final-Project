package com.example.barterbay.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.barterbay.R
import com.example.barterbay.dashboard.DashBoardActivity
import com.example.barterbay.databinding.ActivityRegisterActivtyBinding
import com.example.barterbay.login.LoginViewModel

class RegisterActivty : AppCompatActivity() {
    lateinit var binding:ActivityRegisterActivtyBinding
    lateinit var viewModel:RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_activty)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getClicked().observe(this){
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}