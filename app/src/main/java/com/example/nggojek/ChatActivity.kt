package com.example.nggojek

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnSend = findViewById<ImageView>(R.id.btnSend)
        val edtMessage = findViewById<EditText>(R.id.editMessage)
        val chatContainer = findViewById<LinearLayout>(R.id.chatContainer)

        val chatTitle = intent.getStringExtra("title") ?: "Chat"
        findViewById<TextView>(R.id.txtTitle).text = chatTitle

        btnBack.setOnClickListener {
            finish()
        }

        btnSend.setOnClickListener {
            val message = edtMessage.text.toString()

            if (message.isNotEmpty()) {
                addMessageToChat(message)
                edtMessage.text.clear()
            }
        }
    }

    private fun addMessageToChat(text: String) {
        val chatContainer = findViewById<LinearLayout>(R.id.chatContainer)

        val msgView = TextView(this)
        msgView.text = text
        msgView.textSize = 16f
        msgView.setPadding(12, 8, 12, 8)

        chatContainer.addView(msgView)
    }
}
