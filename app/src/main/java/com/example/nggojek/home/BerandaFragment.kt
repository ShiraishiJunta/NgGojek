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
import com.example.nggojek.R // Import R wajib!

class BerandaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inisialisasi Tombol
        val btnProfile = view.findViewById<ImageView>(R.id.btnProfile)
        val btnMotor = view.findViewById<LinearLayout>(R.id.btnMotor)
        val btnMobil = view.findViewById<LinearLayout>(R.id.btnMobil)

        // 2.Tombol Profile
        btnProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        // 3.Tombol Pesan Mobil
        btnMobil.setOnClickListener {
            val intent = Intent(requireContext(), PesanMobilActivity::class.java)
            startActivity(intent)
        }

        // 4.Tombol Pesan Motor
        btnMotor.setOnClickListener {
             val intent = Intent(requireContext(), PesanMotorActivity::class.java)
             startActivity(intent)
        }
    }
}