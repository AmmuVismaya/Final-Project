package com.example.barterbay.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.barterbay.R
import com.example.barterbay.dashboard.DashBoardAdapter
import com.example.barterbay.databinding.FragmentDashBoardBinding
import com.example.barterbay.databinding.FragmentMyCartBinding
import com.example.barterbay.fragment.adapter.AddProductAdapter
import com.example.barterbay.model.MyCart
import com.example.barterbay.model.ProductDetails
import com.google.firebase.database.*

class MyCartFragment : Fragment() {
    lateinit var mBinding: FragmentMyCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMyCartBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()


    }

    private fun setUpRecycler() {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("MyProduct")

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<MyCart>()
                for (child in snapshot.children) {
                    val product = child.getValue(MyCart::class.java)
                    products.add(product!!)
                    val adapter = AddProductAdapter(products,requireContext())
                    mBinding.myCart.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("", "onCancelled:${error.message} ", )
            }

        })
    }
}


