package com.example.nggojek

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.nggojek.adapter.RiwayatAdapter

class RiwayatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRiwayat: RiwayatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        recyclerView = findViewById(R.id.rvRiwayat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataRiwayat = listOf(
            Riwayat("Nama Driver", "Rp 200.000", 3f),
            Riwayat("Nama Driver", "Rp 200.000", 4f),
            Riwayat("Nama Driver", "Rp 200.000", 2f),
            Riwayat("Nama Driver", "Rp 200.000", 5f),
            Riwayat("Nama Driver", "Rp 200.000", 0f)
        )

        adapterRiwayat = RiwayatAdapter(dataRiwayat)
        recyclerView.adapter = adapterRiwayat
    }
}
