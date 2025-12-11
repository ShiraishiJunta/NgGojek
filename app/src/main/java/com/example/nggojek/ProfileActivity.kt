package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

// =====================================================
// ProfileActivity: Halaman profil pengguna
// Menampilkan informasi akun dan tombol logout
// =====================================================

class ProfileActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Menghubungkan komponen dengan ID dari layout
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnLogout = findViewById<LinearLayout>(R.id.btnLogout)

        // Tombol untuk kembali ke halaman sebelumnya
        btnBack.setOnClickListener { finish() }

        // Tombol untuk logout dan kembali ke halaman login
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // Menghapus semua activity sebelumnya agar tidak bisa kembali
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
