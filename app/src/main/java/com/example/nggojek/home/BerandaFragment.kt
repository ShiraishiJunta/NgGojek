package com.example.nggojek.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.nggojek.order.PesanMobilActivity
import com.example.nggojek.order.PesanMotorActivity
import com.example.nggojek.ProfileActivity
import com.example.nggojek.R

// =====================================================
// BerandaFragment: Halaman beranda/home utama aplikasi
// Berisi banner promo dan 2 tombol untuk memesan kendaraan
// =====================================================

class BerandaFragment : Fragment() {

    // Fungsi untuk membuat tampilan fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Memuat layout dari file XML fragment_beranda
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    // Fungsi yang dipanggil setelah tampilan selesai dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menghubungkan tombol-tombol dengan ID dari layout XML
        val btnProfile = view.findViewById<ImageView>(R.id.btnProfile)
        val btnMotor = view.findViewById<LinearLayout>(R.id.btnMotor)
        val btnMobil = view.findViewById<LinearLayout>(R.id.btnMobil)

        // Tombol untuk membuka halaman profil pengguna
        btnProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        // Tombol untuk membuka halaman pemesanan mobil
        btnMobil.setOnClickListener {
            val intent = Intent(requireContext(), PesanMobilActivity::class.java)
            startActivity(intent)
        }

        // Tombol untuk membuka halaman pemesanan motor
        btnMotor.setOnClickListener {
             val intent = Intent(requireContext(), PesanMotorActivity::class.java)
             startActivity(intent)
        }
    }
}