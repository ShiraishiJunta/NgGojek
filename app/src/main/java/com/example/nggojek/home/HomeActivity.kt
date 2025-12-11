package com.example.nggojek.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.nggojek.ChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

// PENTING: Import R wajib ada agar R.id dan R.layout terbaca
import com.example.nggojek.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.buttonNavButtom)

        if (savedInstanceState == null) {
            loadFragment(BerandaFragment())
        }

        // (Navigasi)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.berandaFragment -> {
                    loadFragment(BerandaFragment())
                    true
                }
                R.id.chatFragment -> {
                    loadFragment(ChatFragment())
                    true
                }
                R.id.riwayatFragment -> {
                    loadFragment(RiwayatFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}