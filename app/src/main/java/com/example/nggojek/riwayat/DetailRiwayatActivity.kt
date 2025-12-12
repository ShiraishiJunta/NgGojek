package com.example.nggojek.riwayat

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R

class DetailRiwayatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat)

        // Inisialisasi Views
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        val tvTotalHarga = findViewById<TextView>(R.id.tvTotalHarga)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)

        val tvDetailJemput = findViewById<TextView>(R.id.tvDetailJemput)
        val tvDetailTujuan = findViewById<TextView>(R.id.tvDetailTujuan)
        val tvUlasanPengguna = findViewById<TextView>(R.id.tvUlasanPengguna)

        //  Ambil Data dari Intent
        val nama = intent.getStringExtra("EXTRA_NAMA") ?: "Driver"
        val harga = intent.getStringExtra("EXTRA_HARGA") ?: "Rp 0"
        val rating = intent.getFloatExtra("EXTRA_RATING", 0f)

        val jemput = intent.getStringExtra("EXTRA_JEMPUT") ?: "-"
        val tujuan = intent.getStringExtra("EXTRA_TUJUAN") ?: "-"
        val layanan = intent.getStringExtra("EXTRA_LAYANAN") ?: "Motor"
        val ulasan = intent.getStringExtra("EXTRA_ULASAN") ?: ""

        // Set Data ke Tampilan
        tvNamaDriver.text = nama
        tvTotalHarga.text = harga
        ratingBar.rating = rating

        tvDetailJemput.text = jemput
        tvDetailTujuan.text = tujuan

        // Logika Tampilan Layanan
        if (layanan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Layanan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Layanan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // Tampilkan Ulasan
        if (ulasan.isNotEmpty()) {
            tvUlasanPengguna.text = "\"$ulasan\""
        } else {
            tvUlasanPengguna.text = "(Tidak ada ulasan)"
        }

        // Tombol Back
        btnBack.setOnClickListener {
            finish()
        }
    }
}