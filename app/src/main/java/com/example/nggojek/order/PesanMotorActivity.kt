package com.example.nggojek.order

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView // 1. IMPORT WAJIB
import com.example.nggojek.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton // 2. IMPORT WAJIB
import com.google.android.material.card.MaterialCardView // 3. IMPORT WAJIB
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.Locale

class PesanMotorActivity : AppCompatActivity() {

    lateinit var mapView: MapView

    // 4. UBAH TIPE KE NestedScrollView
    lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan_motor)

        // Setup Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.blackText))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Setup Map (OSMDroid)
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        val defaultLoc = GeoPoint(-6.200000, 106.816666)
        val controller = mapView.controller
        controller.setZoom(15.0)
        controller.setCenter(defaultLoc)

        // 5. SETUP BOTTOM SHEET (NestedScrollView)
        val bottomSheet = findViewById<NestedScrollView>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        // Agar Map terlihat, set Collapsed awalnya
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // Inisialisasi View
        val inputJemput = findViewById<EditText>(R.id.inputJemput)
        val inputTujuan = findViewById<EditText>(R.id.inputTujuan)
        val btnPesan = findViewById<MaterialButton>(R.id.btnPesan)

        val cardCash = findViewById<MaterialCardView>(R.id.cardCash)
        val cardEW1 = findViewById<MaterialCardView>(R.id.cardEW1)
        val cardEW2 = findViewById<MaterialCardView>(R.id.cardEW2)

        val rbCashRight = findViewById<RadioButton>(R.id.rbCashRight)
        val rbEW1Right = findViewById<RadioButton>(R.id.rbEW1Right)
        val rbEW2Right = findViewById<RadioButton>(R.id.rbEW2Right)

        // Logika Pilihan Pembayaran
        fun selectPaymentMethod(selected: RadioButton) {
            rbCashRight.isChecked = false
            rbEW1Right.isChecked = false
            rbEW2Right.isChecked = false
            selected.isChecked = true
        }

        cardCash.setOnClickListener { selectPaymentMethod(rbCashRight) }
        cardEW1.setOnClickListener { selectPaymentMethod(rbEW1Right) }
        cardEW2.setOnClickListener { selectPaymentMethod(rbEW2Right) }

        rbCashRight.setOnClickListener { selectPaymentMethod(rbCashRight) }
        rbEW1Right.setOnClickListener { selectPaymentMethod(rbEW1Right) }
        rbEW2Right.setOnClickListener { selectPaymentMethod(rbEW2Right) }

        // TOMBOL PESAN
        btnPesan.setOnClickListener {
            val alamatJemput = inputJemput.text.toString().trim()
            val alamatTujuan = inputTujuan.text.toString().trim()

            if (alamatJemput.isEmpty()) {
                inputJemput.error = "Lokasi jemput harus diisi"
                return@setOnClickListener
            }
            if (alamatTujuan.isEmpty()) {
                inputTujuan.error = "Tujuan harus diisi"
                return@setOnClickListener
            }

            if (!rbCashRight.isChecked && !rbEW1Right.isChecked && !rbEW2Right.isChecked) {
                Toast.makeText(this, "Silakan pilih metode pembayaran", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var metodeBayar = "Tunai"
            when {
                rbCashRight.isChecked -> metodeBayar = "Tunai"
                rbEW1Right.isChecked -> metodeBayar = "Gopay"
                rbEW2Right.isChecked -> metodeBayar = "OVO"
            }

            // 6. JALANKAN DI BACKGROUND (Agar Tidak Crash)
            Toast.makeText(this, "Mencari lokasi...", Toast.LENGTH_SHORT).show()

            Thread {
                try {
                    val koordinatJemput = cariKoordinat(alamatJemput)
                    val koordinatTujuan = cariKoordinat(alamatTujuan)

                    // Kembali ke UI Thread
                    runOnUiThread {
                        if (koordinatJemput != null && koordinatTujuan != null) {
                            mapView.controller.animateTo(koordinatJemput)
                            mapView.controller.setZoom(18.0)

                            // Pindah ke Konfirmasi
                            try {
                                val intent = Intent(this@PesanMotorActivity, KonfirmasiActivity::class.java)
                                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
                                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
                                intent.putExtra("EXTRA_LAT_JEMPUT", koordinatJemput.latitude)
                                intent.putExtra("EXTRA_LON_JEMPUT", koordinatJemput.longitude)
                                intent.putExtra("EXTRA_LAT_TUJUAN", koordinatTujuan.latitude)
                                intent.putExtra("EXTRA_LON_TUJUAN", koordinatTujuan.longitude)
                                intent.putExtra("EXTRA_JENIS_KENDARAAN", "Motor")
                                intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
                                intent.putExtra("EXTRA_HARGA", 20000)

                                startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(this@PesanMotorActivity, "Gagal membuka konfirmasi.", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Toast.makeText(this@PesanMotorActivity, "Lokasi tidak ditemukan.", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@PesanMotorActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }

    private fun cariKoordinat(namaTempat: String): GeoPoint? {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val hasil = geocoder.getFromLocationName(namaTempat, 1)
            if (hasil != null && hasil.isNotEmpty()) {
                return GeoPoint(hasil[0].latitude, hasil[0].longitude)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}