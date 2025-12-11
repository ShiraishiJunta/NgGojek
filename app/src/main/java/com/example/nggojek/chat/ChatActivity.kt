package com.example.nggojek.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.R

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerChat: RecyclerView
    private lateinit var edtMessage: EditText
    private lateinit var btnSend: ImageView

    private val chatList = ArrayList<ChatMessage>()

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // nama driver
        val namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"
        findViewById<TextView>(R.id.txtTitle).text = namaDriver

        recyclerChat = findViewById(R.id.recyclerChat)
        edtMessage = findViewById(R.id.editMessage)
        btnSend = findViewById(R.id.btnSend)

        // Inisialisasi Adapter
        chatAdapter = ChatAdapter(chatList)

        recyclerChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerChat.adapter = chatAdapter

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        btnSend.setOnClickListener {
            val msg = edtMessage.text.toString()

            if (msg.isNotEmpty()) {
                // Tambah pesan Kita (true)
                addMessage(msg, true)
                edtMessage.text.clear()

                // Simulasi balasan Driver (false)
                recyclerChat.postDelayed({
                    addMessage("Siap kak, otw.", false)
                }, 1000)
            }
        }
    }

    private fun addMessage(text: String, isSender: Boolean) {
        chatList.add(ChatMessage(text, isSender))
        chatAdapter.notifyItemInserted(chatList.size - 1)
        recyclerChat.scrollToPosition(chatList.size - 1)
    }
}