package com.example.nggojek

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderanBerlangsungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderan_berlangsung)

        // 1. Inisialisasi Views
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvAlamatJemput = findViewById<TextView>(R.id.tvAlamatJemput)
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)
        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val tvHargaLayanan = findViewById<TextView>(R.id.tvHargaLayanan)
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        val tvNopolDriver = findViewById<TextView>(R.id.tvNopolDriver)
        val tvRatingDriver = findViewById<TextView>(R.id.tvRatingDriver)

        // 2. Mengambil data dari Intent
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // 3. Format harga
        val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"

        // 4. Set data ke Views
        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvHargaLayanan.text = hargaString

        // Data dummy driver
        tvNamaDriver.text = "Budi Santoso"
        tvNopolDriver.text = "B 1234 XYZ"
        tvRatingDriver.text = "4.8"

        // 5. Set icon dan nama layanan berdasarkan jenis kendaraan
        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // 6. Back Button
        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
