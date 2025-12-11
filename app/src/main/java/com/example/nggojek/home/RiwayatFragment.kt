package com.example.nggojek.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.riwayat.DetailRiwayatActivity
import com.example.nggojek.R
import com.example.nggojek.riwayat.RiwayatAdapter
import com.example.nggojek.riwayat.RiwayatData

// =====================================================
// RiwayatFragment: Fragment untuk menampilkan riwayat perjalanan
// Menampilkan daftar perjalanan yang sudah selesai
// =====================================================

class RiwayatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRiwayat: RiwayatAdapter

    // Fungsi untuk membuat tampilan fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Memuat layout dari file XML fragment_riwayat
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    // Fungsi yang dipanggil setelah tampilan selesai dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menghubungkan RecyclerView dengan ID dari layout
        recyclerView = view.findViewById(R.id.rvRiwayat)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Membuat adapter dengan data dari RiwayatData
        // Lambda function untuk menangani aksi ketika item diklik
        adapterRiwayat = RiwayatAdapter(RiwayatData.listRiwayat) { selectedRiwayat ->

            // Membuka halaman detail riwayat
            val intent = Intent(requireContext(), DetailRiwayatActivity::class.java)

            // Mengirim semua data riwayat ke halaman detail
            intent.putExtra("EXTRA_NAMA", selectedRiwayat.namaDriver)
            intent.putExtra("EXTRA_HARGA", selectedRiwayat.totalHarga)
            intent.putExtra("EXTRA_RATING", selectedRiwayat.rating)

            // Mengirim data lokasi dan layanan
            intent.putExtra("EXTRA_JEMPUT", selectedRiwayat.alamatJemput)
            intent.putExtra("EXTRA_TUJUAN", selectedRiwayat.alamatTujuan)
            intent.putExtra("EXTRA_LAYANAN", selectedRiwayat.jenisLayanan)

            // Mengirim ulasan pengguna
            intent.putExtra("EXTRA_ULASAN", selectedRiwayat.ulasan)

            startActivity(intent)
        }

        // Memasang adapter ke RecyclerView
        recyclerView.adapter = adapterRiwayat
    }

    // Fungsi yang dipanggil saat fragment kembali ditampilkan
    // Digunakan untuk refresh data riwayat
    override fun onResume() {
        super.onResume()
        // Memperbarui tampilan jika ada data riwayat baru
        if (::adapterRiwayat.isInitialized) {
            adapterRiwayat.notifyDataSetChanged()
        }
    }
}