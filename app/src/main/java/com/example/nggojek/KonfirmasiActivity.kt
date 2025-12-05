package com.example.nggojek

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KonfirmasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        // 1. Inisialisasi Views (Menghubungkan kodingan dengan XML)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvAlamatJemput = findViewById<TextView>(R.id.tvAlamatJemput)
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)
        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val tvHargaLayanan = findViewById<TextView>(R.id.tvHargaLayanan)
        val tvMetodePembayaran = findViewById<TextView>(R.id.tvMetodePembayaran)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val tvTotalRincian = findViewById<TextView>(R.id.tvTotalRincian)
        val tvTotalFooter = findViewById<TextView>(R.id.tvTotalFooter)
        val btnPesan = findViewById<Button>(R.id.btnPesan)

        // 2. Mengambil data dari Halaman Sebelumnya (Intent)
        // Pastikan halaman sebelumnya mengirim data dengan key yang sama (Contoh: "EXTRA_ALAMAT_JEMPUT")
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor" // Default Motor
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0) // Harga berupa angka (Integer)

        // Format Harga ke Rupiah
        val hargaString = "Rp $harga"

        // 3. Menampilkan Data ke Layar
        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvMetodePembayaran.text = metodeBayar

        // Update harga di semua tempat yang menampilkan harga
        tvHargaLayanan.text = hargaString
        tvSubtotal.text = hargaString
        tvTotalRincian.text = hargaString
        tvTotalFooter.text = hargaString

        // Logika untuk menentukan Teks dan Icon berdasarkan jenis kendaraan
        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            // Ganti icon di bawah ini dengan R.drawable.ic_mobil jika sudah ada
            imgTransportIcon.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            // Ganti icon di bawah ini dengan R.drawable.ic_motor jika sudah ada
            imgTransportIcon.setImageResource(R.drawable.ic_launcher_foreground)
        }

        // 4. Aksi Tombol
        btnBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }

        btnPesan.setOnClickListener {
            // Logika ketika tombol pesan ditekan
            Toast.makeText(this, "Pesanan $jenisKendaraan berhasil dibuat!", Toast.LENGTH_SHORT).show()

            // Disini kamu bisa menambahkan kode untuk pindah ke halaman 'Mencari Driver'
            // val intent = Intent(this, SearchingDriverActivity::class.java)
            // startActivity(intent)
        }
    }
}