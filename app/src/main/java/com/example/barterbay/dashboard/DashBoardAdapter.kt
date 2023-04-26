package com.example.barterbay.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barterbay.R
import com.example.barterbay.fragment.ProductDetailFragment
import com.example.barterbay.model.ProductDetails
import com.example.barterbay.model.ProductModel

class DashBoardAdapter(private val mList: List<ProductDetails>,
private val context: Context
) : RecyclerView.Adapter<DashBoardAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val products = mList[position]
        holder.ProductName.text = products.ProductName
        holder.ProductPrice.text = products.ProductPrice
        if (products.ProductImage == "") {
            holder.image.setImageResource(R.drawable.shopkart_placeholder)
        } else {
            Glide.with(context).load(products.ProductImage).placeholder(R.drawable.shopkart_placeholder).into(holder.image)
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ProductName", products.ProductName)
            bundle.putString("ProductPrice", products.ProductPrice)
            bundle.putString("ProductImage", products.ProductImage)
            bundle.putString("ProductImage", products.ProductImage)

            val intent = Intent(context, ProductDetailFragment::class.java)
            intent.putExtras(bundle)
            val fragment = ProductDetailFragment()
            fragment.arguments = bundle
            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }




    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ProductName: TextView = itemView.findViewById(R.id.iv_product_item_title)
        val ProductPrice : TextView = itemView.findViewById(R.id.iv_product_item_price)
        val image : ImageView = itemView.findViewById(R.id.iv_product_item_image)
    }
}