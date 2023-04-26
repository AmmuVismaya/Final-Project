package com.example.barterbay.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.barterbay.R
import com.example.barterbay.databinding.ActivityDashBoardBinding
import com.example.barterbay.databinding.ActivityLoginBinding
import com.example.barterbay.fragment.DashBoardFragment
import com.example.barterbay.fragment.ProductDetailFragment
import com.example.barterbay.profile.ProfileActivity

class DashBoardActivity : AppCompatActivity() {

    lateinit var  binding: ActivityDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_dash_board)
        replaceFragment(DashBoardFragment())
        setUpFragments()
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
    private fun setUpFragments() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboardFragment -> replaceFragment(DashBoardFragment())
                R.id.productFragment -> replaceFragment(ProductDetailFragment())

//            R.id.project -> replaceFragment(Project_Fragment())

                else -> {

                }
            }

            true
        }
    }
}