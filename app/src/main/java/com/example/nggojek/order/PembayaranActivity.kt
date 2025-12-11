package com.example.nggojek.order

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R
import com.example.nggojek.order.RatingActivity

// =====================================================
// PembayaranActivity: Halaman untuk pembayaran
// Menampilkan metode pembayaran dan total yang harus dibayar
// Setelah 5 detik otomatis muncul popup pembayaran berhasil
// =====================================================

class PembayaranActivity : AppCompatActivity() {

    // Variabel untuk menyimpan data pesanan
    private var alamatJemput: String? = null
    private var alamatTujuan: String? = null
    private var jenisKendaraan: String? = null
    private var namaDriver: String? = null
    private var metodeBayar: String? = null
    private var harga: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        // Mengambil semua data dari halaman sebelumnya
        alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT")
        alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN")
        jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN")
        harga = intent.getIntExtra("EXTRA_HARGA", 0)
        metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"

        // Menghubungkan komponen dengan ID dari layout
        val tvTotalBayar = findViewById<TextView>(R.id.tvTotalBayar)
        val tvMetodeTitle = findViewById<TextView>(R.id.tvMetodeTitle)
        val layoutCash = findViewById<LinearLayout>(R.id.layoutCash)
        val layoutEWallet = findViewById<LinearLayout>(R.id.layoutEWallet)
        val tvNamaBank = findViewById<TextView>(R.id.tvNamaBank)
        val tvNoVa = findViewById<TextView>(R.id.tvNoVa)

        // Menampilkan total pembayaran
        tvTotalBayar.text = "Rp ${String.format("%,d", harga).replace(',', '.')}"
        tvMetodeTitle.text = "Metode: $metodeBayar"

        // Menampilkan layout sesuai metode pembayaran yang dipilih
        if (metodeBayar.equals("Tunai", ignoreCase = true)) {
            // Jika pembayaran tunai - tampilkan instruksi bayar tunai
            layoutCash.visibility = View.VISIBLE
            layoutEWallet.visibility = View.GONE
        } else {
            // Jika pembayaran e-wallet - tampilkan nomor virtual account
            layoutCash.visibility = View.GONE
            layoutEWallet.visibility = View.VISIBLE
            tvNamaBank.text = "Virtual Account ($metodeBayar)"
            tvNoVa.text = "8800 1234 5678"
        }

        // Simulasi pembayaran berhasil setelah 5 detik
        Handler(Looper.getMainLooper()).postDelayed({
            tampilkanPopUpSelesai()
        }, 5000)
    }

    // Fungsi untuk menampilkan popup pembayaran berhasil
    private fun tampilkanPopUpSelesai() {
        if (!isFinishing) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pembayaran Berhasil")
            builder.setMessage("Driver telah mengonfirmasi pembayaran Anda.")
            builder.setCancelable(false)

            // Tombol untuk memberi rating
            builder.setPositiveButton("Beri Rating") { dialog, which ->

                // Pindah ke halaman rating
                val intent = Intent(this, RatingActivity::class.java)

                // Mengirim semua data ke halaman rating
                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
                intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
                intent.putExtra("EXTRA_NAMA_DRIVER", namaDriver)
                intent.putExtra("EXTRA_HARGA", harga)

                startActivity(intent)
                finish()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}