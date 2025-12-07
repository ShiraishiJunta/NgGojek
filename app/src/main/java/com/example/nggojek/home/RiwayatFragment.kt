package com.example.nggojek.home // 1. Package berubah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 2. IMPORT FILE DARI LUAR PACKAGE HOME (Jika Adapter tidak ikut dipindah)
import com.example.nggojek.RiwayatAdapter
import com.example.nggojek.Riwayat

// 3. IMPORT Resource (R) - WAJIB
import com.example.nggojek.R

class RiwayatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRiwayat: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRiwayat)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Data Dummy
        val dataRiwayat = listOf(
            Riwayat("Budi Santoso", "Rp 15.000", 4.5f),
            Riwayat("Asep Knalpot", "Rp 20.000", 5.0f),
            Riwayat("Siti Driver", "Rp 12.000", 4.0f)
        )

        adapterRiwayat = RiwayatAdapter(dataRiwayat)
        recyclerView.adapter = adapterRiwayat
    }
}