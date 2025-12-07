package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class PesanMotorActivity : AppCompatActivity() {

    lateinit var mapView: MapView
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        setContentView(R.layout.activity_pesan_motor)

        // --- Setup Toolbar ---
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(getColor(android.R.color.black))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // --- Setup Map ---
        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        val PNM = GeoPoint(-7.6476489, 111.5268208)

        val controller = mapView.controller
        controller.setZoom(20.0)
        controller.setCenter(PNM)

        val marker = Marker(mapView)
        marker.position = PNM
        marker.title = "PNM"
        mapView.overlays.add(marker)

        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        val inputJemput = findViewById<EditText>(R.id.inputJemput)
        val inputTujuan = findViewById<EditText>(R.id.inputTujuan)

        val cardCash = findViewById<CardView>(R.id.cardCash)
        val cardEW1 = findViewById<CardView>(R.id.cardEW1)
        val cardEW2 = findViewById<CardView>(R.id.cardEW2)

        val rbCashRight = findViewById<RadioButton>(R.id.rbCashRight)
        val rbEW1Right = findViewById<RadioButton>(R.id.rbEW1Right)
        val rbEW2Right = findViewById<RadioButton>(R.id.rbEW2Right)

        fun selectPaymentMethod(selected: RadioButton) {
            rbCashRight.isChecked = false
            rbEW1Right.isChecked = false
            rbEW2Right.isChecked = false
            selected.isChecked = true
        }

        cardCash.setOnClickListener {
            selectPaymentMethod(rbCashRight)
        }

        cardEW1.setOnClickListener {
            selectPaymentMethod(rbEW1Right)
        }

        cardEW2.setOnClickListener {
            selectPaymentMethod(rbEW2Right)
        }

        rbCashRight.setOnClickListener {
            selectPaymentMethod(rbCashRight)
        }

        rbEW1Right.setOnClickListener {
            selectPaymentMethod(rbEW1Right)
        }

        rbEW2Right.setOnClickListener {
            selectPaymentMethod(rbEW2Right)
        }

        findViewById<Button>(R.id.btnPesan).setOnClickListener {
            // Ambil data dari input
            val alamatJemput = inputJemput.text.toString().trim()
            val alamatTujuan = inputTujuan.text.toString().trim()

            if (alamatJemput.isEmpty()) {
                inputJemput.error = "Lokasi jemput harus diisi"
                inputJemput.requestFocus()
                return@setOnClickListener
            }

            if (alamatTujuan.isEmpty()) {
                inputTujuan.error = "Tujuan harus diisi"
                inputTujuan.requestFocus()
                return@setOnClickListener
            }

            if (!rbCashRight.isChecked && !rbEW1Right.isChecked && !rbEW2Right.isChecked) {
                Toast.makeText(this, "Silakan pilih metode pembayaran terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val metodeBayar = when {
                rbCashRight.isChecked -> "Tunai"
                rbEW1Right.isChecked -> "E-Wallet 1"
                rbEW2Right.isChecked -> "E-Wallet 2"
                else -> "Tunai"
            }

            val intent = Intent(this, KonfirmasiActivity::class.java)
            intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
            intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
            intent.putExtra("EXTRA_JENIS_KENDARAAN", "Motor")
            intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)
            intent.putExtra("EXTRA_HARGA", 200000)

            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}