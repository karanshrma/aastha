package com.example.aastha

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.example.aastha.databinding.ActivityLoginAdminBinding
import com.google.android.material.navigation.NavigationView

class IntroPage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityLoginAdminBinding  // Use appropriate binding class for your layout

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginAdminBinding.inflate(layoutInflater)  // Inflate the correct layout
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )
        val headerView = binding.navigationDrawer.getHeaderView(0)

        // Access the header views and set up click listener
        val headerButton = headerView.findViewById<TextView>(R.id.LoginMenuNavigationHeader)
        headerButton.setOnClickListener {
            Toast.makeText(this, "Profile Button Clicked!", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }

        val headerImage = headerView.findViewById<ImageView>(R.id.imageViewNH)
        headerImage.setOnClickListener {
            Toast.makeText(this, "Profile Image Clicked!", Toast.LENGTH_SHORT).show()
            // Perform other actions
        }
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationDrawer.setNavigationItemSelectedListener(this)
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this@IntroPage , UserDetails::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.LoginMenuNavigationHeader -> navigateToLogin()
            R.id.testview -> navigateToLogin()
            R.id.navigation_home -> navigateToLogin()

            else -> return false
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateToLogin() {
        try {
            startActivity(Intent(this, AdminLogin::class.java))
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening login page", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> super.onBackPressedDispatcher.onBackPressed()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save any necessary state
    }
}
