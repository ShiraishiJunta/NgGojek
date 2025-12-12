package com.example.nggojek.order

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nggojek.R
import com.example.nggojek.order.RatingActivity

class PembayaranActivity : AppCompatActivity() {

    // Variabel Global
    private var alamatJemput: String? = null
    private var alamatTujuan: String? = null
    private var jenisKendaraan: String? = null
    private var namaDriver: String? = null
    private var metodeBayar: String? = null
    private var harga: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        // MERIMA SEMUA DATA
        alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT")
        alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN")
        jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN")

        harga = intent.getIntExtra("EXTRA_HARGA", 0)
        metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR") ?: "Tunai"
        namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"

        // Inisiasi views dan button
        val tvTotalBayar = findViewById<TextView>(R.id.tvTotalBayar)
        val tvMetodeTitle = findViewById<TextView>(R.id.tvMetodeTitle)
        val layoutCash = findViewById<LinearLayout>(R.id.layoutCash)
        val layoutEWallet = findViewById<LinearLayout>(R.id.layoutEWallet)
        val tvNamaBank = findViewById<TextView>(R.id.tvNamaBank)
        val tvNoVa = findViewById<TextView>(R.id.tvNoVa)
        val btnSalinVa = findViewById<ImageView>(R.id.btnSalinVa)

        tvTotalBayar.text = "Rp ${String.format("%,d", harga).replace(',', '.')}"
        tvMetodeTitle.text = "Metode: $metodeBayar"

        if (metodeBayar.equals("Tunai", ignoreCase = true)) {
            layoutCash.visibility = View.VISIBLE
            layoutEWallet.visibility = View.GONE
        } else {
            layoutCash.visibility = View.GONE
            layoutEWallet.visibility = View.VISIBLE
            tvNamaBank.text = "Virtual Account ($metodeBayar)"
            tvNoVa.text = "8800 1234 5678"
        }

        // Listener untuk menyalin teks
        btnSalinVa.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Nomor VA", tvNoVa.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Nomor VA berhasil disalin!", Toast.LENGTH_SHORT).show()
        }

        // Timer Simulasi Bayar
        Handler(Looper.getMainLooper()).postDelayed({
            tampilkanPopUpSelesai()
        }, 5000)
    }

    private fun tampilkanPopUpSelesai() {
        if (!isFinishing) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pembayaran Berhasil")
            builder.setMessage("Driver telah mengonfirmasi pembayaran Anda.")
            builder.setCancelable(false)

            builder.setPositiveButton("Beri Rating") { dialog, which ->

                val intent = Intent(this, RatingActivity::class.java)

                // MENGIRIM SEMUA DATA KE HALAMAN RATING
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