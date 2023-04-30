package com.example.barterbay.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.barterbay.R
import com.example.barterbay.databinding.FragmentAddProuctBinding
import com.example.barterbay.fragment.adapter.AddProductAdapter
import com.example.barterbay.model.ProductDetails
import com.example.barterbay.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class AddProuctFragment : Fragment() {
    lateinit var binding: FragmentAddProuctBinding

    val storageRef = FirebaseStorage.getInstance().reference
    private var filePath: Uri? = null
    var downloadUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProuctBinding.inflate(inflater, container, false)

        binding.ivCameraIcon.setOnClickListener {
            chooseImage()
        }
        binding.buttonSaveButton.setOnClickListener {
saveProduct()
        }
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            filePath = data?.data
            val fileRef = storageRef.child("images/${filePath?.lastPathSegment}")
            val uploadTask = filePath?.let { fileRef.putFile(it) }
            uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                fileRef.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result.toString()
                    binding.ivProductImage.setImageURI(filePath)
                    //saveProduct()
                } else {
                    // Handle errors
                }
            }
        }
    }

    private fun saveProduct() {
        val productName = binding.productName.text.toString()
        val price = binding.price.text.toString()
        val description = binding.description.text.toString()

        val databaseRef = FirebaseDatabase.getInstance().reference.child("MyProduct")
        val datas = HashMap<String, String>()
        datas["ProductName"] = productName
        datas["Price"] = price
        datas["Description"] = description
        datas["ImageUrl"] = downloadUri.toString()

        databaseRef.push().setValue(datas)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT)
                    .show()
                clearForm()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Failed to add product: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun clearForm() {
        binding.productName.text?.clear()
        binding.price.text?.clear()
        binding.description.text?.clear()

    }
}




