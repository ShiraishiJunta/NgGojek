package com.example.nggojek.order

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.nggojek.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.Locale

// =====================================================
// PesanMotorActivity: Halaman untuk memesan layanan motor
// Pengguna mengisi lokasi jemput, tujuan, dan metode bayar
// =====================================================

class PesanMotorActivity : AppCompatActivity() {

    lateinit var mapView: MapView
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan_motor)

        // Mengatur toolbar dengan tombol kembali
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(getColor(android.R.color.black))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Mengatur peta OSM (OpenStreetMap)
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        // Lokasi awal peta (PNM)
        val defaultLoc = GeoPoint(-7.6476489, 111.5268208)
        val controller = mapView.controller
        controller.setZoom(20.0)
        controller.setCenter(defaultLoc)

        // Mengatur bottom sheet untuk input formulir
        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        // Menghubungkan komponen input dengan ID dari layout
        val inputJemput = findViewById<EditText>(R.id.inputJemput)
        val inputTujuan = findViewById<EditText>(R.id.inputTujuan)
        val btnPesan = findViewById<Button>(R.id.btnPesan)

        // Komponen untuk metode pembayaran
        val cardCash = findViewById<CardView>(R.id.cardCash)
        val cardEW1 = findViewById<CardView>(R.id.cardEW1)
        val cardEW2 = findViewById<CardView>(R.id.cardEW2)

        val rbCashRight = findViewById<RadioButton>(R.id.rbCashRight)
        val rbEW1Right = findViewById<RadioButton>(R.id.rbEW1Right)
        val rbEW2Right = findViewById<RadioButton>(R.id.rbEW2Right)

        // Fungsi untuk memilih metode pembayaran
        fun selectPaymentMethod(selected: RadioButton) {
            rbCashRight.isChecked = false
            rbEW1Right.isChecked = false
            rbEW2Right.isChecked = false
            selected.isChecked = true
        }

        // Tombol untuk memilih pembayaran tunai
        cardCash.setOnClickListener { selectPaymentMethod(rbCashRight) }
        cardEW1.setOnClickListener { selectPaymentMethod(rbEW1Right) }
        cardEW2.setOnClickListener { selectPaymentMethod(rbEW2Right) }

        rbCashRight.setOnClickListener { selectPaymentMethod(rbCashRight) }
        rbEW1Right.setOnClickListener { selectPaymentMethod(rbEW1Right) }
        rbEW2Right.setOnClickListener { selectPaymentMethod(rbEW2Right) }

        // Tombol untuk melanjutkan ke halaman konfirmasi
        btnPesan.setOnClickListener {
            // Mengambil input dari pengguna
            val alamatJemput = inputJemput.text.toString().trim()
            val alamatTujuan = inputTujuan.text.toString().trim()

            // Validasi: lokasi jemput harus diisi
            if (alamatJemput.isEmpty()) {
                inputJemput.error = "Lokasi jemput harus diisi"
                return@setOnClickListener
            }
            
            // Validasi: tujuan harus diisi
            if (alamatTujuan.isEmpty()) {
                inputTujuan.error = "Tujuan harus diisi"
                return@setOnClickListener
            }

            // Validasi: metode pembayaran harus dipilih
            if (!rbCashRight.isChecked && !rbEW1Right.isChecked && !rbEW2Right.isChecked) {
                Toast.makeText(this, "Silakan pilih metode pembayaran terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Menentukan nama metode pembayaran
            var metodeBayar = "Tunai"
            when {
                rbCashRight.isChecked -> metodeBayar = "Tunai"
                rbEW1Right.isChecked -> metodeBayar = "Gopay"
                rbEW2Right.isChecked -> metodeBayar = "OVO"
            }

            // Mencari koordinat GPS dari nama tempat
            val koordinatJemput = cariKoordinat(alamatJemput)
            val koordinatTujuan = cariKoordinat(alamatTujuan)

            if (koordinatJemput != null && koordinatTujuan != null) {
                // Jika lokasi ditemukan, pindahkan peta ke lokasi jemput
                mapView.controller.animateTo(koordinatJemput)
                mapView.controller.setZoom(18.0)

                // Menyiapkan data untuk dikirim ke halaman konfirmasi
                val intent = Intent(this, KonfirmasiActivity::class.java)

                // Mengirim alamat (teks)
                intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
                intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)

                // Mengirim koordinat GPS (untuk tampilan peta nanti)
                intent.putExtra("EXTRA_LAT_JEMPUT", koordinatJemput.latitude)
                intent.putExtra("EXTRA_LON_JEMPUT", koordinatJemput.longitude)
                intent.putExtra("EXTRA_LAT_TUJUAN", koordinatTujuan.latitude)
                intent.putExtra("EXTRA_LON_TUJUAN", koordinatTujuan.longitude)

                // Mengirim informasi lainnya
                intent.putExtra("EXTRA_JENIS_KENDARAAN", "Motor")
                intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
                intent.putExtra("EXTRA_HARGA", 20000)

                startActivity(intent)

            } else {
                // Jika lokasi tidak ditemukan
                Toast.makeText(this, "Lokasi tidak ditemukan. Coba ketik nama tempat yang lebih jelas.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Fungsi untuk mengubah nama tempat menjadi koordinat GPS
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