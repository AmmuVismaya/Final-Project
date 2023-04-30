package com.example.barterbay.fragment.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barterbay.R
import com.example.barterbay.fragment.ProductDetailFragment
import com.example.barterbay.model.MyCart
import com.example.barterbay.model.ProductDetails

class AddProductAdapter (private val mList: List<MyCart>,
                         private val context: Context
) : RecyclerView.Adapter<AddProductAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val products = mList[position]
        holder.ProductName.text = products.ProductName
        holder.ProductPrice.text = products.Price
        if (products.ImageUrl == "") {
            holder.image.setImageResource(R.drawable.shopkart_placeholder)
        } else {
            Glide.with(context).load(products.ImageUrl)
                .placeholder(R.drawable.shopkart_placeholder).into(holder.image)
        }



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ProductName: TextView = itemView.findViewById(R.id.iv_cart_item_title)
        val ProductPrice: TextView = itemView.findViewById(R.id.iv_product_item_price)
        val image: ImageView = itemView.findViewById(R.id.iv_cart_item_image)
    }
}