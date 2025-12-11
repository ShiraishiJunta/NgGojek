package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderanBerlangsungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderan_berlangsung)

        // Tampilkan notifikasi awal
        Toast.makeText(this, "Driver ditemukan! Menuju lokasi jemput...", Toast.LENGTH_SHORT).show()

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

        // 2. Mengambil data dari Intent (Dari MencariDriverActivity)
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong" // Data diterima disini
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Cash"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // Ambil Koordinat juga (jika ada) untuk diteruskan
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        val namaDriver = "Budi Santoso"

        // 3. Format harga & Set View
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

        btnBack.setOnClickListener { finish() }

        // ==================================================================
        // LOGIKA OTOMATIS PINDAH HALAMAN
        // ==================================================================

        // Delay 5 detik (Simulasi Driver jalan)
        Handler(Looper.getMainLooper()).postDelayed({

            Toast.makeText(this, "Driver sudah sampai di titik jemput!", Toast.LENGTH_LONG).show()

            // Delay 2 detik lagi lalu pindah
            Handler(Looper.getMainLooper()).postDelayed({

                val intent = Intent(this, PerjalananActivity::class.java)

                // --- BAGIAN PENTING YANG SEBELUMNYA KURANG ---

                // 1. KIRIM ULANG ALAMAT JEMPUT (Inilah penyebab datanya hilang sebelumnya)
                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)

                // 2. Kirim data lainnya
                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
                intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
                intent.putExtra("EXTRA_NAMA_DRIVER", namaDriver)
                intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
                intent.putExtra("EXTRA_HARGA", harga)

                // 3. Kirim Koordinat
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