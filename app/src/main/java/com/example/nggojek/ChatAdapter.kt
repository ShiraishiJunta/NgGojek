package com.example.nggojek

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messageList: ArrayList<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutContainer: LinearLayout = itemView.findViewById(R.id.layoutContainer)
        val cardBubble: CardView = itemView.findViewById(R.id.cardBubble)
        val txtMessage: TextView = itemView.findViewById(R.id.txtMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        // Panggil layout item_chat_message yang baru dibuat
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = messageList[position]

        holder.txtMessage.text = item.message

        if (item.isSender) {
            // Jika KITA yang kirim: Rata Kanan, Warna Hijau
            holder.layoutContainer.gravity = Gravity.END
            holder.cardBubble.setCardBackgroundColor(0xFFDCF8C6.toInt())
        } else {
            // Jika DRIVER yang kirim: Rata Kiri, Warna Putih
            holder.layoutContainer.gravity = Gravity.START
            holder.cardBubble.setCardBackgroundColor(0xFFFFFFFF.toInt())
        }
    }

    override fun getItemCount(): Int = messageList.size
}