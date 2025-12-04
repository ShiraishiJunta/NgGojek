package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class HomepageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val btnProfile = findViewById<ImageView>(R.id.btnProfile)
        val btnMotor = findViewById<LinearLayout>(R.id.btnMotor)
        val btnMobil = findViewById<LinearLayout>(R.id.btnMobil)

        val navHome = findViewById<ImageView>(R.id.navHome)
        val navChat = findViewById<ImageView>(R.id.navChat)
        val navRiwayat = findViewById<ImageView>(R.id.navRiwayat)
//        val navProfile = findViewById<ImageView>(R.id.navProfile)
        val navSettings = findViewById<ImageView>(R.id.navSettings)

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
//
        btnMotor.setOnClickListener {
            startActivity(Intent(this, PesanMotorActivity::class.java))
        }
//
//        btnMobil.setOnClickListener {
//            startActivity(Intent(this, MobilActivity::class.java))
//        }

        navChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

        navRiwayat.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }

        navProfile.setOnClickListener {
//
//        navProfile.setOnClickListener {
//            startActivity(Intent(this, ProfileActivity::class.java))
//        }

        navSettings.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
