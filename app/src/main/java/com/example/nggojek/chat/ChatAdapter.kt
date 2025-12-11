package com.example.nggojek.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.R

// =====================================================
// ChatAdapter: Adapter untuk menampilkan pesan chat
// Bubble kanan = pesan dari pengguna
// Bubble kiri = pesan dari driver
// =====================================================

class ChatAdapter(private val messageList: ArrayList<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutContainer: LinearLayout = itemView.findViewById(R.id.layoutContainer)
        val cardBubble: CardView = itemView.findViewById(R.id.cardBubble)
        val txtMessage: TextView = itemView.findViewById(R.id.txtMessage)
    }

    // Membuat tampilan untuk setiap item chat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    // Mengatur tampilan setiap pesan
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = messageList[position]

        // Menampilkan isi pesan
        holder.txtMessage.text = item.message

        // Mengatur posisi dan warna bubble
        if (item.isSender) {
            // Pesan dari pengguna: bubble kanan, warna hijau
            holder.layoutContainer.gravity = Gravity.END
            holder.cardBubble.setCardBackgroundColor(0xFFDCF8C6.toInt())
        } else {
            // Pesan dari driver: bubble kiri, warna putih
            holder.layoutContainer.gravity = Gravity.START
            holder.cardBubble.setCardBackgroundColor(0xFFFFFFFF.toInt())
        }
    }

    // Mengembalikan jumlah total pesan
    override fun getItemCount(): Int = messageList.size
}