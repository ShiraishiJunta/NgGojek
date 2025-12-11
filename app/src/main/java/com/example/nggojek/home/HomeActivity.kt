package com.example.nggojek.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.nggojek.ChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.nggojek.R

// =====================================================
// HomeActivity: Halaman utama setelah login berhasil
// Menampilkan 3 menu di bottom navigation:
// - Beranda, Chat, Riwayat
// =====================================================

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Mengatur padding agar tidak tertutup status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Menghubungkan bottom navigation dengan ID dari layout
        val bottomNav = findViewById<BottomNavigationView>(R.id.buttonNavButtom)

        // Menampilkan fragment Beranda saat pertama kali dibuka
        if (savedInstanceState == null) {
            loadFragment(BerandaFragment())
        }

        // Mengatur aksi ketika menu bottom navigation diklik
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // Menu Beranda diklik - tampilkan BerandaFragment
                R.id.berandaFragment -> {
                    loadFragment(BerandaFragment())
                    true
                }
                // Menu Chat diklik - tampilkan ChatFragment  
                R.id.chatFragment -> {
                    loadFragment(ChatFragment())
                    true
                }
                // Menu Riwayat diklik - tampilkan RiwayatFragment
                R.id.riwayatFragment -> {
                    loadFragment(RiwayatFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Fungsi untuk mengganti fragment yang ditampilkan
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}