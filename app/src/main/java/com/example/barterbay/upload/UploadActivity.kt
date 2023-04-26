package com.example.barterbay.upload

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.barterbay.R
import com.example.barterbay.databinding.ActivityUploadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference
    var database = FirebaseDatabase.getInstance()
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST: Int = 2020
    private lateinit var storageRef: StorageReference
    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload)
        binding.add.setOnClickListener {
            chooseImage()
        }
        binding.save.setOnClickListener {
            uploadImage()
        }
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode != null) {
            filePath = data!!.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.userImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        if (filePath != null) {

            val productName = binding.productName.text.toString()
            val price = binding.prdouctPrice.text.toString()
            val root = database.getReference("Product").child(productName)
            databaseReference =
                FirebaseDatabase.getInstance().getReference("Product").child(productName)
            val hashMap: HashMap<String, String> = HashMap()
            hashMap.put("ProductName", productName)
            hashMap.put("ProductPrice", price)
            hashMap.put("description", "")
            hashMap.put("ProductImage:", "")


            root.setValue(hashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }

                val ref: StorageReference = storageRef.child("Products/" + UUID.randomUUID().toString())
                ref.putFile(filePath!!)
                    .addOnSuccessListener {


                        ref.downloadUrl.addOnSuccessListener { uri ->
                            val downloadUrl = uri.toString()
                            Log.e("TAG", "uploadImage:$downloadUrl ",)
                            val hashMap: HashMap<String, String> = HashMap()
                            hashMap.put("ProductImage", downloadUrl)
                            databaseReference.updateChildren(hashMap as Map<String, Any>)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Profile image updated",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Failed to update profile image: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }


                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Failed" + it.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }

            }
        }
    }
}