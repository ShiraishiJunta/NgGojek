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

class MencariDriverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mencari_driver)

        // 1. Inisialisasi Views
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvAlamatJemput = findViewById<TextView>(R.id.tvAlamatJemput)
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val imgTransportIcon = findViewById<ImageView>(R.id.imgTransportIcon)
        val tvJenisLayanan = findViewById<TextView>(R.id.tvJenisLayanan)
        val tvHargaLayanan = findViewById<TextView>(R.id.tvHargaLayanan)
        val btnBatalkan = findViewById<Button>(R.id.btnBatalkan)

        // 2. Mengambil data dari Intent (TERMASUK KOORDINAT)
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "Alamat Jemput Kosong"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "Alamat Tujuan Kosong"
        val jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"
        val metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // AMBIL KOORDINAT PENTING
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        // 3. Format harga
        val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"

        // 4. Set data ke Views
        tvAlamatJemput.text = alamatJemput
        tvAlamatTujuan.text = alamatTujuan
        tvHargaLayanan.text = hargaString

        // 5. Set icon dan nama layanan
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

        // 7. Tombol Batalkan (Penting untuk menghentikan Handler jika user keluar)
        // Kita simpan referensi handler agar bisa dibatalkan
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            // Logika pindah halaman setelah delay
            val intent = Intent(this, OrderanBerlangsungActivity::class.java) // Atau PerjalananActivity

            // OPER DATA TEKS
            intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
            intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
            intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
            intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
            intent.putExtra("EXTRA_HARGA", harga)

            // OPER DATA KOORDINAT
            intent.putExtra("EXTRA_LAT_JEMPUT", latJemput)
            intent.putExtra("EXTRA_LON_JEMPUT", lonJemput)
            intent.putExtra("EXTRA_LAT_TUJUAN", latTujuan)
            intent.putExtra("EXTRA_LON_TUJUAN", lonTujuan)

            // Simulasi nama driver
            intent.putExtra("EXTRA_NAMA_DRIVER", "Budi Santoso")

            startActivity(intent)
            finish()
        }

        // Mulai Timer 3 Detik
        handler.postDelayed(runnable, 3000)

        // Tombol Batal
        btnBatalkan.setOnClickListener {
            // Hentikan timer agar tidak pindah halaman sendiri setelah dibatalkan
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