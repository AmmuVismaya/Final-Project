package com.example.barterbay.profile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.barterbay.R
import com.example.barterbay.databinding.ActivityProfileBinding
import com.example.barterbay.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var filePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        setUpProfile()
    }

    private fun setUpProfile() {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        databaseReference.addValueEventListener(object : ValueEventListener {
            val image = binding.ivProfileImage
            val fname = binding.fname
            val lName = binding.lName
            val email = binding.temail
            override fun onDataChange(snapshot: DataSnapshot) {
                val profile = snapshot.getValue(Profile::class.java)
                fname.setText("${profile?.FirstName}")
                lName.setText("${profile?.LastName}")
                email.setText("${profile?.Email}")
                if (profile?.ProfileImage == "") {
                    image.setImageResource(R.drawable.ic_add)
                } else {
                    Glide.with(this@ProfileActivity).load(profile?.ProfileImage).into(image)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
        binding.ivProfileImage.setOnClickListener {
            chooseImage()
        }
        binding.buttonSaveButton.setOnClickListener {
            uploadImage()
        }

    }

    private fun chooseImage() {
        val intent: Intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode != null) {
            filePath = data!!.data
            try {
                val bitmap: Bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.ivProfileImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if (filePath != null) {

            val ref: StorageReference =
                storageRef.child("Profile/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("ProfileImage", filePath.toString())
                    databaseReference.updateChildren(hashMap as Map<String, Any>)

                    Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()


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


