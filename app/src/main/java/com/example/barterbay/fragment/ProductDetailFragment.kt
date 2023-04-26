package com.example.barterbay.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.barterbay.R
import com.example.barterbay.databinding.FragmentDashBoardBinding
import com.example.barterbay.databinding.FragmentProductDetailBinding


class ProductDetailFragment : Fragment() {
    lateinit var mBinding: FragmentProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return mBinding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName = arguments?.getString("ProductName")
        val productPrice = arguments?.getString("ProductPrice")
        val prductImages = arguments?.getString("ProductImage")
        mBinding.ivProductDetailTitle.text = productName
        mBinding.ivProductDetailPrice.text =productPrice

         Glide.with(requireContext()).load(prductImages).into(mBinding.ivProductDetailImage)
        mBinding.buttonAddToCart.setOnClickListener {
            mBinding.buttonGoToCart.visibility == View.VISIBLE

        }




    }



}


