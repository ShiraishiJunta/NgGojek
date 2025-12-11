package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R
import com.google.android.material.button.MaterialButton

class KonfirmasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        // 1. Inisialisasi Views (Sesuai XML yang baru)
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

        // Gunakan MaterialButton
        val btnPesan = findViewById<MaterialButton>(R.id.btnPesan)

        // 2. Mengambil data dari Intent
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "-"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "-"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        // 3. Format Data ke UI
        val hargaFormat = "Rp ${String.format("%,d", harga).replace(',', '.')}"
        val totalBayar = harga + 2000 // Tambah biaya aplikasi
        val totalFormat = "Rp ${String.format("%,d", totalBayar).replace(',', '.')}"

        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvMetodePembayaran.text = metodeBayar

        tvHargaLayanan.text = hargaFormat
        tvSubtotal.text = hargaFormat

        // Total sudah termasuk biaya aplikasi
        tvTotalRincian.text = totalFormat
        tvTotalFooter.text = totalFormat

        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            tvJenisLayanan.text = "Pesan Mobil"
            imgTransportIcon.setImageResource(R.drawable.car)
        } else {
            tvJenisLayanan.text = "Pesan Motor"
            imgTransportIcon.setImageResource(R.drawable.motor)
        }

        // 4. Button Logic
        btnBack.setOnClickListener {
            finish()
        }

        btnPesan.setOnClickListener {
            // Cek apakah Activity MencariDriverActivity ada
            try {
                val intent = Intent(this, MencariDriverActivity::class.java)

                // Oper Data lagi
                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
                intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
                intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
                intent.putExtra("EXTRA_HARGA", totalBayar) // Kirim harga total

                startActivity(intent)
                finish() // Tutup konfirmasi agar tidak bisa back
            } catch (e: Exception) {
                Toast.makeText(this, "Halaman Driver belum siap", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}