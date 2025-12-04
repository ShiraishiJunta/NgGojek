package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

//        val btnRiwayat = findViewById<LinearLayout>(R.id.btnRiwayat)
        val btnLogout = findViewById<LinearLayout>(R.id.btnLogout)

//        // menuju RiwayatActivity
//        btnRiwayat.setOnClickListener {
//            startActivity(Intent(this, RiwayatActivity::class.java))
//        }

        // Logout kembali ke LoginActivity
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
