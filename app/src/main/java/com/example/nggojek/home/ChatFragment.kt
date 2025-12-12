package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.chat.ChatActivity
import com.example.nggojek.chat.ChatInboxAdapter
import com.example.nggojek.chat.InboxModel

// =====================================================
// ChatFragment: Fragment untuk menampilkan daftar inbox chat
// Menampilkan daftar percakapan dengan driver
// =====================================================

class ChatFragment : Fragment() {

    // Fungsi untuk membuat tampilan fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Memuat layout dari file XML fragment_chat
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    // Fungsi yang dipanggil setelah tampilan selesai dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menghubungkan RecyclerView dengan ID dari layout
        val rvChatInbox = view.findViewById<RecyclerView>(R.id.rvChatInbox)
        rvChatInbox.layoutManager = LinearLayoutManager(requireContext())

        // Data dummy untuk daftar inbox (simulasi)
        // Dalam aplikasi real, data ini akan diambil dari server
        val inboxData = listOf(
            InboxModel("Nama Driver", "Saya udah di depan kak", "11:00"),
            InboxModel("Supir Ojol", "Oke siap, ditunggu ya", "10:45"),
            InboxModel("Mang Bakso", "Pesanan sudah sampai", "09:30"),
            InboxModel("Mi ayam", "Enak", "10:00")
        )

        // Membuat adapter dan mengatur aksi ketika item diklik
        val adapter = ChatInboxAdapter(inboxData) { selectedChat ->
            
            // Membuka halaman chat ketika inbox diklik
            val intent = Intent(requireContext(), ChatActivity::class.java)

            // Mengirim nama driver ke halaman chat
            intent.putExtra("EXTRA_NAMA_DRIVER", selectedChat.name)

            startActivity(intent)
        }

        // Memasang adapter ke RecyclerView
        rvChatInbox.adapter = adapter
    }
}