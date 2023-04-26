package com.example.barterbay.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.barterbay.dashboard.DashBoardAdapter
import com.example.barterbay.databinding.FragmentDashBoardBinding
import com.example.barterbay.model.ProductDetails
import com.google.firebase.database.*


class DashBoardFragment : Fragment() {
    lateinit var mBinding: FragmentDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentDashBoardBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()


    }

    private fun setUpRecycler() {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Product")

        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<ProductDetails>()
                for (child in snapshot.children) {
                    val product = child.getValue(ProductDetails::class.java)
                    products.add(product!!)
                    val adapter = DashBoardAdapter(products,requireContext())
                    mBinding.rvDashboardItems.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("", "onCancelled:${error.message} ", )
            }

        })
    }
}

