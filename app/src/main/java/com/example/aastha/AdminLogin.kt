
package com.example.aastha

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aastha.databinding.ActivityAdminLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class AdminLogin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        auth = Firebase.auth
        // Add this to your Application class or MainActivity
        Firebase.initialize(this)

        if (auth.currentUser!=null)
        {
            startMainActivity()
        }

        val button = binding.login // Bind the login button
        button.setOnClickListener {
            val username = binding.adminUser.text.toString()
            val password = binding.adminPass.text.toString()

            when {
                username.isEmpty() -> {
                    binding.adminUser.error = "Please enter username"
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.adminPass.error = "Please enter password"
                    return@setOnClickListener
                }
                username != "admin" -> {
                    binding.adminUser.error = "Invalid username"
                    return@setOnClickListener
                }
                else -> {
                    loginUser("ks2275863@gmail.com", password)
                }
            }

        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("LoginActivity", "signInWithEmail:success")
                    startMainActivity()
                } else {
                    // If sign in fails, display a message to the user
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}

//package com.example.aastha
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import com.example.aastha.databinding.ActivityAdminLoginBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.ktx.initialize
//
//class AdminLogin : AppCompatActivity() {
//    private lateinit var binding: ActivityAdminLoginBinding
//    private val database = Firebase.database.reference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        enableEdgeToEdge()
//
//        Firebase.initialize(this)
//
//        binding.login.setOnClickListener {
//            val username = binding.adminUser.text.toString()
//            val password = binding.adminPass.text.toString()
//
//            when {
//                username.isEmpty() -> {
//                    binding.adminUser.error = "Please enter username"
//                    return@setOnClickListener
//                }
//                password.isEmpty() -> {
//                    binding.adminPass.error = "Please enter password"
//                    return@setOnClickListener
//                }
//                else -> {
//                    verifyUserCredentials(username, password)
//                }
//            }
//        }
//    }
//
//    private fun verifyUserCredentials(username: String, password: String) {
//        binding.login.isEnabled = false
//
//        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var userFound = false
//
//                for (userSnapshot in snapshot.children) {
//                    val dbUsername = userSnapshot.child("name").getValue(String::class.java)
//                    val dbPassword = userSnapshot.child("password").getValue(String::class.java)
//
//                    if (dbUsername == username && dbPassword == password) {
//                        userFound = true
//                        startMainActivity()
//                        break
//                    }
//                }
//
//                if (!userFound) {
//                    Toast.makeText(
//                        this@AdminLogin,
//                        "Invalid username or password",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                binding.login.isEnabled = true
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("AdminLogin", "Database error: ${error.message}")
//                Toast.makeText(
//                    this@AdminLogin,
//                    "Error: ${error.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//                binding.login.isEnabled = true
//            }
//        })
//    }
//
//    private fun startMainActivity() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//}
