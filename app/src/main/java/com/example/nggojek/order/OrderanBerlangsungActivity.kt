package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.nggojek.R
import com.example.nggojek.chat.ChatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class OrderanBerlangsungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderan_berlangsung)

        // BOTTOM SHEET
        try {
            val bottomSheet = findViewById<NestedScrollView>(R.id.bottomSheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Tampilkan notifikasi awal
        Toast.makeText(this, "Driver ditemukan! Menuju lokasi jemput...", Toast.LENGTH_SHORT).show()

        // Inisialisasi Views
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvAlamatJemput = findViewById<TextView>(R.id.tvAlamatJemput)
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)
        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val tvHargaLayanan = findViewById<TextView>(R.id.tvHargaLayanan)
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        val tvNopolDriver = findViewById<TextView>(R.id.tvNopolDriver)
        val tvRatingDriver = findViewById<TextView>(R.id.tvRatingDriver)

        // Inisialisasi Tombol Chat
        val btnChat = findViewById<ImageView>(R.id.btnChat)

        // Mengambil data dari Intent
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "-"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "-"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Cash"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        val namaDriver = "Budi Santoso"

        // Format harga & Set View
        val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"
        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvHargaLayanan.text = hargaString
        tvNamaDriver.text = namaDriver
        tvNopolDriver.text = "B 1234 XYZ"
        tvRatingDriver.text = "4.8"

        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // LOGIKA TOMBOL BACK
        btnBack.setOnClickListener { finish() }

        // LOGIKA TOMBOL CHAT
        btnChat.setOnClickListener {
            val intentChat = Intent(this, ChatActivity::class.java)
            intentChat.putExtra("EXTRA_NAMA_DRIVER", namaDriver)
            startActivity(intentChat)
        }

        // SIMULASI PERJALANAN (5 Detik)
        Handler(Looper.getMainLooper()).postDelayed({

            Toast.makeText(this, "Driver sudah sampai di titik jemput!", Toast.LENGTH_LONG).show()

            // Delay 2 detik lagi lalu pindah ke PerjalananActivity
            Handler(Looper.getMainLooper()).postDelayed({

                val intent = Intent(this, PerjalananActivity::class.java)

                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
                intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
                intent.putExtra("EXTRA_NAMA_DRIVER", namaDriver)
                intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
                intent.putExtra("EXTRA_HARGA", harga)

                startActivity(intent)
                finish()

            }, 2000)

        }, 5000)
    }
}