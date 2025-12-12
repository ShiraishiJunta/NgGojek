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

// =====================================================
// KonfirmasiActivity: Halaman konfirmasi pesanan
// Menampilkan ringkasan pesanan sebelum mencari driver
// =====================================================

class KonfirmasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        // Menghubungkan komponen dengan ID dari layout
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
        tvMetodePembayaran.text = metodeBayar
        tvHargaLayanan.text = hargaString
        tvSubtotal.text = hargaString
        tvTotalRincian.text = hargaString
        tvTotalFooter.text = hargaString

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

        // Tombol untuk mengonfirmasi pesanan dan mencari driver
        btnPesan.setOnClickListener {
            Toast.makeText(this, "Pesanan $jenisKendaraan berhasil dibuat!", Toast.LENGTH_SHORT).show()

            // Membuka halaman mencari driver
            val intent = Intent(this, MencariDriverActivity::class.java)

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

            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

// =====================================================
// RatingActivity: Halaman untuk memberi rating driver
// Ditampilkan setelah perjalanan selesai
// =====================================================

//class RatingActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_rating)
//
//        // Mengambil data dari halaman sebelumnya
//        val namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"
//        val harga = intent.getIntExtra("EXTRA_HARGA", 0)
//        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Lokasi Jemput"
//        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Lokasi Tujuan"
//        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
//
//        // Menghubungkan komponen dengan ID dari layout
//        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
//        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
//        val btnKirim = findViewById<Button>(R.id.btnKirim)
//        val edtUlasan = findViewById<TextInputEditText>(R.id.edtUlasan)
//
//        tvNamaDriver.text = namaDriver
//
//        // Tombol untuk mengirim rating dan ulasan
//        btnKirim.setOnClickListener {
//            val nilaiRating = ratingBar.rating
//            val pesanUlasan = edtUlasan.text.toString()
//
//            // Validasi: rating harus diisi
//            if (nilaiRating == 0f) {
//                Toast.makeText(this, "Silakan beri bintang", Toast.LENGTH_SHORT).show()
//            } else {
//                val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"
//
//                // Menyimpan riwayat perjalanan
//                val riwayatBaru = Riwayat(
//                    namaDriver = namaDriver,
//                    totalHarga = hargaString,
//                    rating = nilaiRating,
//                    alamatJemput = alamatJemput,
//                    alamatTujuan = alamatTujuan,
//                    jenisLayanan = jenisKendaraan,
//                    ulasan = pesanUlasan
//                )
//
//                // Menambahkan ke daftar riwayat
//                RiwayatData.listRiwayat.add(0, riwayatBaru)
//
//                Toast.makeText(this, "Terima kasih!", Toast.LENGTH_SHORT).show()
//
//                // Kembali ke halaman home
//                val intent = Intent(this, HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
//}