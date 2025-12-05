package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Panggil layout fragment_chat yang baru dibuat
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvChatInbox = view.findViewById<RecyclerView>(R.id.rvChatInbox)
        rvChatInbox.layoutManager = LinearLayoutManager(requireContext())

        // 1. Buat Data Dummy (Sesuai Screenshot)
        val inboxData = listOf(
            InboxModel("Nama Driver", "Saya udah di depan kak", "11:00"),
            InboxModel("Supir Ojol", "Oke siap, ditunggu ya", "10:45"),
            InboxModel("Mang Bakso", "Pesanan sudah sampai", "09:30")
        )

        // 2. Pasang Adapter dengan aksi KLIK
        val adapter = ChatInboxAdapter(inboxData) { selectedChat ->

            // --- INI LOGIKA SAAT ITEM DIKLIK ---
            // Pindah ke ChatActivity (Halaman kirim pesan yang kamu punya)
            val intent = Intent(requireContext(), ChatActivity::class.java)

            // (Opsional) Kirim nama driver ke halaman chat
            intent.putExtra("EXTRA_NAMA_DRIVER", selectedChat.name)

            startActivity(intent)
        }

        rvChatInbox.adapter = adapter
    }
}