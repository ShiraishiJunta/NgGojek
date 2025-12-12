package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R

// =====================================================
// OrderanBerlangsungActivity: Halaman orderan berlangsung
// Menampilkan info driver yang ditemukan
// =====================================================

class OrderanBerlangsungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderan_berlangsung)

        // Menampilkan notifikasi driver ditemukan
        Toast.makeText(this, "Driver ditemukan! Menuju lokasi jemput...", Toast.LENGTH_SHORT).show()

        // Menghubungkan komponen dengan ID dari layout
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvAlamatJemput = findViewById<TextView>(R.id.tvAlamatJemput)
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)
        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val tvHargaLayanan = findViewById<TextView>(R.id.tvHargaLayanan)
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        val tvNopolDriver = findViewById<TextView>(R.id.tvNopolDriver)
        val tvRatingDriver = findViewById<TextView>(R.id.tvRatingDriver)

        // Mengambil data dari halaman sebelumnya
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Cash"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // Mengambil koordinat GPS
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        val namaDriver = "Budi Santoso"

        // Mengubah harga menjadi format Rupiah
        val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"
        
        // Menampilkan data ke layar
        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvHargaLayanan.text = hargaString
        tvNamaDriver.text = namaDriver
        tvNopolDriver.text = "B 1234 XYZ"
        tvRatingDriver.text = "4.8"

        // Menampilkan jenis layanan dan icon yang sesuai
        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // Tombol untuk kembali ke halaman sebelumnya
        btnBack.setOnClickListener { finish() }

        // Simulasi: Driver sampai setelah 5 detik
        Handler(Looper.getMainLooper()).postDelayed({

            Toast.makeText(this, "Driver sudah sampai di titik jemput!", Toast.LENGTH_LONG).show()

            // Simulasi: Pindah ke halaman perjalanan setelah 2 detik lagi
            Handler(Looper.getMainLooper()).postDelayed({

                val intent = Intent(this, PerjalananActivity::class.java)

                // Mengirim semua data untuk perjalanan
                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
                intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
                intent.putExtra("EXTRA_NAMA_DRIVER", namaDriver)
                intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
                intent.putExtra("EXTRA_HARGA", harga)

                // Mengirim koordinat GPS
                intent.putExtra("EXTRA_LAT_JEMPUT", latJemput)
                intent.putExtra("EXTRA_LON_JEMPUT", lonJemput)
                intent.putExtra("EXTRA_LAT_TUJUAN", latTujuan)
                intent.putExtra("EXTRA_LON_TUJUAN", lonTujuan)

                startActivity(intent)
                finish()

            }, 2000)

        }, 5000)
    }
}