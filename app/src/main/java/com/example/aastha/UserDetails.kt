package com.example.aastha

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aastha.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserDetails : AppCompatActivity() {
    private lateinit var binding : ActivityUserDetailsBinding
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = FirebaseFirestore.getInstance()
        binding.saveUserDetailsBtn.setOnClickListener {
            saveUserDetails()
        }
    }
    private fun saveUserDetails() {
        val sponsorNumber = binding.sponsor.text.toString().trim()
        val proposeNumber = binding.propose.text.toString().trim()

        // Input validation
        if (sponsorNumber.isEmpty() || proposeNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userMap = hashMapOf(
            "sponsorNumber" to sponsorNumber,
            "proposeNumber" to proposeNumber
        )

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        // Show loading indicator (you can add a ProgressBar in your layout)
        binding.saveUserDetailsBtn.isEnabled = false

        db.collection("Vendor").document(userId).set(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                binding.sponsor.text.clear()
                binding.propose.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("FireStore" , "Error: ${e.message}")
                Log.e("Firestore", "Error adding document", e)
            }
            .addOnCompleteListener {
                // Re-enable the button
                binding.saveUserDetailsBtn.isEnabled = true
            }
    }



}