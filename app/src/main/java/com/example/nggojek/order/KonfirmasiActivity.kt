package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R
import com.example.nggojek.home.HomeActivity
import com.example.nggojek.riwayat.Riwayat
import com.example.nggojek.riwayat.RiwayatData
import com.google.android.material.textfield.TextInputEditText

class KonfirmasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        // 1. Inisialisasi Views
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

        // 2. Mengambil data dari Halaman Sebelumnya (PesanMobil/Motor)
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // AMBIL DATA KOORDINAT (Agar map di akhir nanti bisa jalan)
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        // 3. Set Teks
        val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"

        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvMetodePembayaran.text = metodeBayar

        tvHargaLayanan.text = hargaString
        tvSubtotal.text = hargaString
        tvTotalRincian.text = hargaString
        tvTotalFooter.text = hargaString

        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // 4. Back BUTTON
        btnBack.setOnClickListener {
            finish()
        }

        // 5. Tombol Pesan (Lanjut ke Cari Driver / Perjalanan)
        btnPesan.setOnClickListener {
            Toast.makeText(this, "Pesanan $jenisKendaraan berhasil dibuat!", Toast.LENGTH_SHORT).show()

            // Pindah ke halaman 'Mencari Driver' (Punya temanmu)
            // Pastikan nama activity di bawah ini benar (sesuai kode temanmu)
            val intent = Intent(this, MencariDriverActivity::class.java)

            // OPER SEMUA DATA AGAR TIDAK HILANG
            intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
            intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
            intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
            intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
            intent.putExtra("EXTRA_HARGA", harga)

            // OPER KOORDINAT JUGA
            intent.putExtra("EXTRA_LAT_JEMPUT", latJemput)
            intent.putExtra("EXTRA_LON_JEMPUT", lonJemput)
            intent.putExtra("EXTRA_LAT_TUJUAN", latTujuan)
            intent.putExtra("EXTRA_LON_TUJUAN", lonTujuan)

            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

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