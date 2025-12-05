package com.example.nggojek

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerChat: RecyclerView
    private lateinit var edtMessage: EditText
    private lateinit var btnSend: ImageView

    private val chatList = ArrayList<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val namaDriver = intent.getStringExtra("EXTRA_NAMA_DRIVER") ?: "Driver"
        findViewById<TextView>(R.id.txtTitle).text = "namaDriver"

        recyclerChat = findViewById(R.id.recyclerChat)
        edtMessage = findViewById(R.id.editMessage)
        btnSend = findViewById(R.id.btnSend)

        chatAdapter = ChatAdapter(chatList)

        recyclerChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerChat.adapter = chatAdapter

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        btnSend.setOnClickListener {
            val msg = edtMessage.text.toString()

            if (msg.isNotEmpty()) {
                addMessage(msg, true)
                edtMessage.text.clear()

                recyclerChat.postDelayed({
                    addMessage("Iyo sek to sabar.", false)
                }, 600)
            }
        }
    }

    private fun addMessage(text: String, isSender: Boolean) {
        chatList.add(ChatMessage(text, isSender))
        chatAdapter.notifyItemInserted(chatList.size - 1)
        recyclerChat.scrollToPosition(chatList.size - 1)
    }
}
