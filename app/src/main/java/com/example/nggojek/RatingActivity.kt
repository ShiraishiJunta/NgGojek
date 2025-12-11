package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.home.HomeActivity
import com.google.android.material.textfield.TextInputEditText

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        // 1. TERIMA DATA DARI PEMBAYARAN
        val namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // Data yang sebelumnya hilang, sekarang sudah sampai sini
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Lokasi Jemput"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Lokasi Tujuan"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"

        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val btnKirim = findViewById<Button>(R.id.btnKirim)
        val edtUlasan = findViewById<TextInputEditText>(R.id.edtUlasan)

        tvNamaDriver.text = namaDriver

        btnKirim.setOnClickListener {
            val nilaiRating = ratingBar.rating
            val pesanUlasan = edtUlasan.text.toString()

            if (nilaiRating == 0f) {
                Toast.makeText(this, "Silakan beri bintang", Toast.LENGTH_SHORT).show()
            } else {

                val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"

                // 2. SIMPAN KE SINGLETON RIWAYAT
                // Pastikan class Riwayat.kt sudah punya field lengkap (jemput, tujuan, layanan, ulasan)
                val riwayatBaru = Riwayat(
                    namaDriver = namaDriver,
                    totalHarga = hargaString,
                    rating = nilaiRating,
                    alamatJemput = alamatJemput,  // Data sudah benar
                    alamatTujuan = alamatTujuan,  // Data sudah benar
                    jenisLayanan = jenisKendaraan, // Data sudah benar
                    ulasan = pesanUlasan
                )

                // Masukkan ke list
                RiwayatData.listRiwayat.add(0, riwayatBaru)

                Toast.makeText(this, "Terima kasih!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}