package com.example.nggojek.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.R

// =====================================================
// ChatActivity: Halaman untuk chat dengan driver
// Menampilkan percakapan dengan bubble chat
// =====================================================

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerChat: RecyclerView
    private lateinit var edtMessage: EditText
    private lateinit var btnSend: ImageView

    // Daftar untuk menyimpan semua pesan
    private val chatList = ArrayList<ChatMessage>()

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Mengambil nama driver dari halaman sebelumnya
        val namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"
        findViewById<TextView>(R.id.txtTitle).text = namaDriver

        // Menghubungkan komponen dengan ID dari layout
        recyclerChat = findViewById(R.id.recyclerChat)
        edtMessage = findViewById(R.id.editMessage)
        btnSend = findViewById(R.id.btnSend)

        // Mengatur adapter untuk menampilkan chat
        chatAdapter = ChatAdapter(chatList)
        recyclerChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerChat.adapter = chatAdapter

        // Tombol untuk kembali ke halaman sebelumnya
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        // Tombol untuk mengirim pesan
        btnSend.setOnClickListener {
            val msg = edtMessage.text.toString()

            if (msg.isNotEmpty()) {
                // Menambahkan pesan dari pengguna
                addMessage(msg, true)
                edtMessage.text.clear()

                // Simulasi balasan otomatis dari driver
                recyclerChat.postDelayed({
                    addMessage("Saya udah di depan, kak.", false)
                }, 1000)
            }
        }
    }

    // Fungsi untuk menambahkan pesan baru
    // true = pesan dari pengguna (kanan)
    // false = pesan dari driver (kiri)
    private fun addMessage(text: String, isSender: Boolean) {
        chatList.add(ChatMessage(text, isSender))
        chatAdapter.notifyItemInserted(chatList.size - 1)
        recyclerChat.scrollToPosition(chatList.size - 1)
    }
}