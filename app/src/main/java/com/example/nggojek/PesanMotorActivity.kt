package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
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
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        setContentView(R.layout.activity_pesan_motor)


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


        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)


        findViewById<Button>(R.id.btnPesan).setOnClickListener {
            startActivity(Intent(this, KonfirmasiActivity::class.java))
        }
    }
}

