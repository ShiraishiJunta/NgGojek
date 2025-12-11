package com.example.nggojek.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.riwayat.DetailRiwayatActivity // Nanti kita buat ini
import com.example.nggojek.R
import com.example.nggojek.riwayat.RiwayatAdapter
import com.example.nggojek.riwayat.RiwayatData

class RiwayatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRiwayat: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRiwayat)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Masukkan Lambda function untuk menangani klik
        adapterRiwayat = RiwayatAdapter(RiwayatData.listRiwayat) { selectedRiwayat ->

            val intent = Intent(requireContext(), DetailRiwayatActivity::class.java)

            // OPER SEMUA DATA DARI OBJECT RIWAYAT
            intent.putExtra("EXTRA_NAMA", selectedRiwayat.namaDriver)
            intent.putExtra("EXTRA_HARGA", selectedRiwayat.totalHarga)
            intent.putExtra("EXTRA_RATING", selectedRiwayat.rating)

            // Lokasi & Layanan
            intent.putExtra("EXTRA_JEMPUT", selectedRiwayat.alamatJemput)
            intent.putExtra("EXTRA_TUJUAN", selectedRiwayat.alamatTujuan)
            intent.putExtra("EXTRA_LAYANAN", selectedRiwayat.jenisLayanan)

            // Ulasan
            intent.putExtra("EXTRA_ULASAN", selectedRiwayat.ulasan)

            startActivity(intent)
        }

        recyclerView.adapter = adapterRiwayat
    }

    override fun onResume() {
        super.onResume()
        if (::adapterRiwayat.isInitialized) {
            adapterRiwayat.notifyDataSetChanged()
        }
    }
}