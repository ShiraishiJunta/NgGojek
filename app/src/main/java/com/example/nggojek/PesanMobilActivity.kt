package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class PesanMobilActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan_mobil)

        // --- Setup Toolbar ---
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon?.setTint(getColor(android.R.color.black))
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // --- Setup Map (OSMDroid) ---
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        val jakarta = GeoPoint(-6.2, 106.816666)
        val controller = mapView.controller
        controller.setZoom(14.0)
        controller.setCenter(jakarta)

        val marker = Marker(mapView)
        marker.position = jakarta
        marker.title = "Jakarta"
        mapView.overlays.add(marker)

        // --- Setup Bottom Sheet ---
        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)


        // --- LOGIKA UTAMA: Mengirim Data ke Konfirmasi ---

        // 1. Definisikan komponen input
        val inputJemput = findViewById<EditText>(R.id.inputJemput)
        val inputTujuan = findViewById<EditText>(R.id.inputTujuan)
        val groupRightRadio = findViewById<RadioGroup>(R.id.groupRightRadio)
        val btnPesan = findViewById<Button>(R.id.btnPesan)

        btnPesan.setOnClickListener {
            // 2. Ambil data teks dari EditText
            val alamatJemput = inputJemput.text.toString().trim()
            val alamatTujuan = inputTujuan.text.toString().trim()

            // 3. Validasi sederhana (Cek apakah kosong)
            if (alamatJemput.isEmpty()) {
                inputJemput.error = "Lokasi jemput harus diisi"
                return@setOnClickListener
            }
            if (alamatTujuan.isEmpty()) {
                inputTujuan.error = "Tujuan harus diisi"
                return@setOnClickListener
            }

            // 4. Cek Metode Pembayaran yang dipilih
            // Default ke "Tunai" jika tidak ada yang dipilih atau jika struktur layout radio group bersarang
            var metodeBayar = "Tunai"

            // Kita cek ID mana yang aktif
            val selectedId = groupRightRadio.checkedRadioButtonId

            when (selectedId) {
                R.id.rbCashRight -> metodeBayar = "Tunai"
                R.id.rbEW1Right -> metodeBayar = "E-Wallet 1"
                R.id.rbEW2Right -> metodeBayar = "E-Wallet 2"
            }

            // 5. Masukkan data ke dalam Intent
            val intent = Intent(this, KonfirmasiActivity::class.java)

            intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
            intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
            intent.putExtra("EXTRA_JENIS_KENDARAAN", "Mobil") // Karena ini halaman Pesan Mobil
            intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)

            // Harga kita set sesuai tampilan di XML kamu (Rp 200.000)
            // Atau sesuaikan dengan logika hitungan jarak nanti
            intent.putExtra("EXTRA_HARGA", 200000)

            // 6. Jalankan Activity Konfirmasi
            startActivity(intent)
        }
    }
}