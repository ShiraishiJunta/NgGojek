package com.example.nggojek

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class PerjalananActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var markerKendaraan: Marker
    private lateinit var tvStatus: TextView
    private lateinit var tvEstimasi: TextView

    // Variabel Global untuk menampung data
    private var alamatJemput: String? = null
    private var alamatTujuan: String? = null
    private var jenisKendaraan: String? = null
    private var namaDriver: String? = null
    private var metodeBayar: String? = null
    private var harga: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Konfigurasi OSMDroid
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        setContentView(R.layout.activity_perjalanan)

        // 1. AMBIL SEMUA DATA DARI INTENT
        alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT")
        alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN")
        jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN")
        namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER")
        metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR")
        harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // Ambil Koordinat (agar map sesuai lokasi asli)
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        // --- SETUP VIEW ---
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        tvStatus = findViewById(R.id.tvStatusPerjalanan)
        tvEstimasi = findViewById(R.id.tvEstimasi)

        tvAlamatTujuan.text = alamatTujuan ?: "Tujuan"
        tvNamaDriver.text = namaDriver ?: "Driver"

        // --- SETUP MAP ---
        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        // Tentukan Titik Awal & Akhir
        // Jika koordinat ada (tidak 0.0), pakai koordinat asli. Jika tidak, pakai dummy Jakarta.
        val startPoint = if (latJemput != 0.0) GeoPoint(latJemput, lonJemput) else GeoPoint(-6.200000, 106.816666)
        val endPoint = if (latTujuan != 0.0) GeoPoint(latTujuan, lonTujuan) else GeoPoint(-6.175392, 106.827153)

        val controller = mapView.controller
        controller.setZoom(15.0)
        controller.setCenter(startPoint)

        // 1. MARKER KENDARAAN (Start)
        markerKendaraan = Marker(mapView)
        markerKendaraan.position = startPoint
        markerKendaraan.title = "Kendaraan"
        markerKendaraan.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Ganti Icon sesuai kendaraan
        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            markerKendaraan.icon = ContextCompat.getDrawable(this, R.drawable.car)
        } else {
            markerKendaraan.icon = ContextCompat.getDrawable(this, R.drawable.motor)
        }
        mapView.overlays.add(markerKendaraan)

        // 2. MARKER TUJUAN (End)
        val markerTujuan = Marker(mapView)
        markerTujuan.position = endPoint
        markerTujuan.title = "Tujuan"
        markerTujuan.icon = ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground)
        mapView.overlays.add(markerTujuan)

        // 3. GAMBAR GARIS RUTE (Polyline)
        val line = Polyline()
        line.addPoint(startPoint)
        line.addPoint(endPoint)
        line.color = Color.BLUE
        line.width = 10f
        mapView.overlays.add(line)

        mapView.invalidate()

        // --- MULAI ANIMASI PERJALANAN ---
        Handler(Looper.getMainLooper()).postDelayed({
            mulaiAnimasiPerjalanan(startPoint, endPoint)
        }, 1000)
    }

    private fun mulaiAnimasiPerjalanan(start: GeoPoint, end: GeoPoint) {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 5000 // Durasi animasi 5 detik
        animator.interpolator = LinearInterpolator()

        animator.addUpdateListener { animation ->
            val v = animation.animatedValue as Float

            // Rumus interpolasi koordinat
            val lat = start.latitude + (end.latitude - start.latitude) * v
            val lon = start.longitude + (end.longitude - start.longitude) * v

            val currentPos = GeoPoint(lat, lon)
            markerKendaraan.position = currentPos

            // Update estimasi
            val sisaWaktu = (5 - (5 * v)).toInt()
            if (sisaWaktu > 0) {
                tvEstimasi.text = "Estimasi tiba dalam $sisaWaktu detik"
            } else {
                tvEstimasi.text = "Tiba di lokasi"
            }

            // Kamera mengikuti kendaraan
            mapView.controller.setCenter(currentPos)
            mapView.invalidate()
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                tvStatus.text = "Perjalanan Selesai!"
                tvStatus.setTextColor(Color.BLUE)
                Toast.makeText(this@PerjalananActivity, "Sampai di tujuan!", Toast.LENGTH_SHORT).show()

                // Delay 2 detik sebelum pindah ke pembayaran
                Handler(Looper.getMainLooper()).postDelayed({
                    keHalamanPembayaran()
                }, 2000)
            }
        })
        animator.start()
    }

    private fun keHalamanPembayaran() {
        val intent = Intent(this, PembayaranActivity::class.java)

        intent.putExtra("EXTRA_ALAMAT_JEMPUT", alamatJemput)
        intent.putExtra("EXTRA_ALAMAT_TUJUAN", alamatTujuan)
        intent.putExtra("EXTRA_JENIS_KENDARAAN", jenisKendaraan)
        intent.putExtra("EXTRA_NAMA_DRIVER", namaDriver)
        intent.putExtra("EXTRA_HARGA", harga)
        intent.putExtra("EXTRA_METODE_BAYAR", metodeBayar)

        startActivity(intent)
        finish()
    }
}