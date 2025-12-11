package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R

// =====================================================
// MencariDriverActivity: Halaman untuk mencari driver
// Menampilkan animasi pencarian selama 3 detik
// Setelah itu otomatis pindah ke halaman orderan berlangsung
// =====================================================

class MencariDriverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mencari_driver)

        // Menghubungkan komponen dengan ID dari layout
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvAlamatJemput = findViewById<TextView>(R.id.tvAlamatJemput)
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)
        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val tvHargaLayanan = findViewById<TextView>(R.id.tvHargaLayanan)
        val btnBatalkan = findViewById<Button>(R.id.btnBatalkan)

        // Mengambil data dari halaman sebelumnya
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // Mengambil koordinat GPS
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        // Mengubah harga menjadi format Rupiah
        val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"

        // Menampilkan data ke layar
        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvHargaLayanan.text = hargaString

        // Menampilkan jenis layanan dan icon yang sesuai
        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // Tombol untuk kembali ke halaman sebelumnya
        btnBack.setOnClickListener {
            finish()
        }

        // Mengatur timer untuk otomatis mencari driver
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            // Setelah 3 detik, pindah ke halaman orderan berlangsung
            val intent = Intent(this, OrderanBerlangsungActivity::class.java)

            // Mengirim semua data pesanan
            intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
            intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
            intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
            intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
            intent.putExtra("EXTRA_HARGA", harga)

            // Mengirim koordinat GPS
            intent.putExtra("EXTRA_LAT_JEMPUT", latJemput)
            intent.putExtra("EXTRA_LON_JEMPUT", lonJemput)
            intent.putExtra("EXTRA_LAT_TUJUAN", latTujuan)
            intent.putExtra("EXTRA_LON_TUJUAN", lonTujuan)

            // Mengirim nama driver (simulasi)
            intent.putExtra("EXTRA_NAMA_DRIVER", "Budi Santoso")

            startActivity(intent)
            finish()
        }

        // Memulai timer 3 detik
        handler.postDelayed(runnable, 3000)

        // Tombol untuk membatalkan pencarian driver
        btnBatalkan.setOnClickListener {
            // Menghentikan timer agar tidak pindah halaman otomatis
            handler.removeCallbacks(runnable)

            Toast.makeText(this, "Pencarian driver dibatalkan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}