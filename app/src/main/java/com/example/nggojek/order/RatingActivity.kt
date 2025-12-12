package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R
import com.example.nggojek.home.HomeActivity
import com.example.nggojek.riwayat.Riwayat
import com.example.nggojek.riwayat.RiwayatData
import com.google.android.material.textfield.TextInputEditText

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        // Inisialisasi View
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val edtUlasan = findViewById<TextInputEditText>(R.id.edtUlasan)
        val btnKirim = findViewById<Button>(R.id.btnKirim)

        // Mengambil Data dari Intent
        val namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Budi Santoso"
        val harga = intent.getIntExtra("EXTRA_HARGA", 0)
        val alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT") ?: "-"
        val alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN") ?: "-"
        val jenisLayanan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN") ?: "Motor"

        // Menampilkan Data ke Layar
        tvNamaDriver.text = namaDriver

        // Logika Tombol Kirim
        btnKirim.setOnClickListener {
            val ratingNilai = ratingBar.rating
            val teksUlasan = edtUlasan.text.toString()

            // Validasi: Rating tidak boleh kosong
            if (ratingNilai == 0f) {
                Toast.makeText(this, "Mohon beri bintang untuk driver", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Format Harga menjadi Rupiah
            val hargaString = "Rp ${String.format("%,d", harga).replace(',', '.')}"

            // MENYIMPAN KE DATA RIWAYAT
            try {
                val riwayatBaru = Riwayat(
                    namaDriver = namaDriver,
                    totalHarga = hargaString,
                    rating = ratingNilai,
                    alamatJemput = alamatJemput,
                    alamatTujuan = alamatTujuan,
                    jenisLayanan = jenisLayanan,
                    ulasan = teksUlasan
                )

                RiwayatData.listRiwayat.add(0, riwayatBaru)

            } catch (e: Exception) {
                e.printStackTrace()
                // Jika error saat menyimpan aplikasi tetap jalan
            }

            Toast.makeText(this, "Terima kasih atas penilaiannya!", Toast.LENGTH_SHORT).show()

            // KEMBALI KE HOME
            // Menggunakan flag agar user tidak bisa tekan 'Back' kembali ke rating
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}