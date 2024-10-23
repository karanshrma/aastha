package com.example.aastha

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aastha.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        auth = Firebase.auth

        setupUI()
    }

    private fun setupUI() {
        binding.addTeamMember.setOnClickListener {
            val intent = Intent(this@MainActivity , AddNewActivity::class.java)
            startActivity(intent)
        }
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this@MainActivity , IntroPage::class.java)
            startActivity(intent)
        }
    }
}