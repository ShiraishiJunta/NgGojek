package com.example.nggojek.order

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
import com.example.nggojek.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

// =====================================================
// PerjalananActivity: Halaman perjalanan dengan peta
// Menampilkan animasi kendaraan bergerak dari titik jemput ke tujuan
// Setelah sampai, otomatis pindah ke halaman pembayaran
// =====================================================

class PerjalananActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var markerKendaraan: Marker
    private lateinit var tvStatus: TextView
    private lateinit var tvEstimasi: TextView

    // Variabel untuk menyimpan data pesanan
    private var alamatJemput: String? = null
    private var alamatTujuan: String? = null
    private var jenisKendaraan: String? = null
    private var namaDriver: String? = null
    private var metodeBayar: String? = null
    private var harga: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Konfigurasi peta OSMDroid
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        setContentView(R.layout.activity_perjalanan)

        // Mengambil semua data dari halaman sebelumnya
        alamatJemput = intent.getStringExtra("EXTRA_ALAMAT_JEMPUT")
        alamatTujuan = intent.getStringExtra("EXTRA_ALAMAT_TUJUAN")
        jenisKendaraan = intent.getStringExtra("EXTRA_JENIS_KENDARAAN")
        namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER")
        metodeBayar = intent.getStringExtra("EXTRA_METODE_BAYAR")
        harga = intent.getIntExtra("EXTRA_HARGA", 0)

        // Mengambil koordinat GPS lokasi jemput dan tujuan
        val latJemput = intent.getDoubleExtra("EXTRA_LAT_JEMPUT", 0.0)
        val lonJemput = intent.getDoubleExtra("EXTRA_LON_JEMPUT", 0.0)
        val latTujuan = intent.getDoubleExtra("EXTRA_LAT_TUJUAN", 0.0)
        val lonTujuan = intent.getDoubleExtra("EXTRA_LON_TUJUAN", 0.0)

        // Menghubungkan komponen dengan ID dari layout
        val tvAlamatTujuan = findViewById<TextView>(R.id.tvAlamatTujuan)
        val tvNamaDriver = findViewById<TextView>(R.id.tvNamaDriver)
        tvStatus = findViewById(R.id.tvStatusPerjalanan)
        tvEstimasi = findViewById(R.id.tvEstimasi)

        // Menampilkan data ke layar
        tvAlamatTujuan.text = alamatTujuan ?: "Tujuan"
        tvNamaDriver.text = namaDriver ?: "Driver"

        // Mengatur peta
        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        // Menentukan titik awal (lokasi jemput) dan titik akhir (tujuan)
        // Jika koordinat ada, gunakan koordinat asli. Jika tidak, gunakan lokasi default Jakarta
        val startPoint = if (latJemput != 0.0) GeoPoint(latJemput, lonJemput) else GeoPoint(-6.200000, 106.816666)
        val endPoint = if (latTujuan != 0.0) GeoPoint(latTujuan, lonTujuan) else GeoPoint(-6.175392, 106.827153)

        // Mengatur zoom dan posisi awal peta
        val controller = mapView.controller
        controller.setZoom(15.0)
        controller.setCenter(startPoint)

        // Membuat marker untuk kendaraan
        markerKendaraan = Marker(mapView)
        markerKendaraan.position = startPoint
        markerKendaraan.title = "Kendaraan"
        markerKendaraan.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Mengatur icon kendaraan sesuai jenis layanan
        if (jenisKendaraan.equals("Mobil", ignoreCase = true)) {
            markerKendaraan.icon = ContextCompat.getDrawable(this, R.drawable.car)
        } else {
            markerKendaraan.icon = ContextCompat.getDrawable(this, R.drawable.motor)
        }
        mapView.overlays.add(markerKendaraan)

        // Membuat marker untuk tujuan
        val markerTujuan = Marker(mapView)
        markerTujuan.position = endPoint
        markerTujuan.title = "Tujuan"
        markerTujuan.icon = ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground)
        mapView.overlays.add(markerTujuan)

        // Menggambar garis rute dari lokasi jemput ke tujuan
        val line = Polyline()
        line.addPoint(startPoint)
        line.addPoint(endPoint)
        line.color = Color.BLUE
        line.width = 10f
        mapView.overlays.add(line)

        mapView.invalidate()

        // Memulai animasi perjalanan setelah 1 detik
        Handler(Looper.getMainLooper()).postDelayed({
            mulaiAnimasiPerjalanan(startPoint, endPoint)
        }, 1000)
    }

    // Fungsi untuk menganimasi kendaraan bergerak dari titik awal ke tujuan
    private fun mulaiAnimasiPerjalanan(start: GeoPoint, end: GeoPoint) {
        // Membuat animator untuk animasi pergerakan
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 10000 // Durasi animasi 5 detik
        animator.interpolator = LinearInterpolator()

        // Update posisi kendaraan setiap frame animasi
        animator.addUpdateListener { animation ->
            val v = animation.animatedValue as Float

            // Menghitung posisi kendaraan saat ini dengan interpolasi
            val lat = start.latitude + (end.latitude - start.latitude) * v
            val lon = start.longitude + (end.longitude - start.longitude) * v

            val currentPos = GeoPoint(lat, lon)
            markerKendaraan.position = currentPos

            // Memperbarui estimasi waktu tiba
            val sisaWaktu = (10 - (10 * v)).toInt()
            if (sisaWaktu > 0) {
                tvEstimasi.text = "Estimasi tiba dalam $sisaWaktu detik"
            } else {
                tvEstimasi.text = "Tiba di lokasi"
            }

            // Membuat kamera mengikuti kendaraan
            mapView.controller.setCenter(currentPos)
            mapView.invalidate()
        }

        // Mengatur aksi setelah animasi selesai
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

            // Dipanggil ketika animasi selesai (kendaraan sampai di tujuan)
            override fun onAnimationEnd(p0: Animator) {
                tvStatus.text = "Perjalanan Selesai!"
                tvStatus.setTextColor(Color.BLUE)
                Toast.makeText(this@PerjalananActivity, "Sampai di tujuan!", Toast.LENGTH_SHORT).show()

                // Menunggu 2 detik sebelum pindah ke halaman pembayaran
                Handler(Looper.getMainLooper()).postDelayed({
                    keHalamanPembayaran()
                }, 2000)
            }
        })
        
        // Memulai animasi
        animator.start()
    }

    // Fungsi untuk pindah ke halaman pembayaran
    private fun keHalamanPembayaran() {
        val intent = Intent(this, PembayaranActivity::class.java)

        // Mengirim semua data ke halaman pembayaran
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