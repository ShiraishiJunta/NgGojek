package com.example.nggojek.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.nggojek.order.PesanMobilActivity
import com.example.nggojek.order.PesanMotorActivity
import com.example.nggojek.ProfileActivity
import com.example.nggojek.R
import com.google.android.material.card.MaterialCardView

class BerandaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Tombol
        val btnProfile = view.findViewById<ImageView>(R.id.btnProfile)
        val btnMotor = view.findViewById<MaterialCardView>(R.id.btnMotor)
        val btnMobil = view.findViewById<MaterialCardView>(R.id.btnMobil)

        // Tombol Profile
        btnProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        // Pesan Mobil
        btnMobil.setOnClickListener {
            val intent = Intent(requireContext(), PesanMobilActivity::class.java)
            startActivity(intent)
        }

        // Pesan Motor
        btnMotor.setOnClickListener {
            val intent = Intent(requireContext(), PesanMotorActivity::class.java)
            startActivity(intent)
        }
    }
}