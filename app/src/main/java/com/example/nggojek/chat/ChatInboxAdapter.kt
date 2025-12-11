package com.example.nggojek.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.R

// =====================================================
// ChatInboxAdapter: Adapter untuk menampilkan daftar inbox chat
// Menampilkan daftar percakapan dengan driver
// Setiap item menampilkan nama, pesan terakhir, dan waktu
// =====================================================

class ChatInboxAdapter(
    private val inboxList: List<InboxModel>,      // Daftar inbox yang akan ditampilkan
    private val onItemClick: (InboxModel) -> Unit // Fungsi yang dipanggil saat item diklik
) : RecyclerView.Adapter<ChatInboxAdapter.InboxViewHolder>() {

    // ViewHolder untuk menyimpan referensi komponen dalam setiap item
    class InboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)       // Nama driver
        val tvPreview: TextView = itemView.findViewById(R.id.tvPreview) // Preview pesan terakhir
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)       // Waktu pesan terakhir
    }

    // Membuat tampilan untuk setiap item inbox
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_box, parent, false)
        return InboxViewHolder(view)
    }

    // Mengatur tampilan untuk setiap item
    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
        val item = inboxList[position]

        // Menampilkan data inbox
        holder.tvName.text = item.name       // Nama driver
        holder.tvPreview.text = item.message // Pesan terakhir
        holder.tvTime.text = item.time       // Waktu kirim

        // Aksi ketika item inbox diklik - buka halaman chat
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    // Mengembalikan jumlah total item inbox
    override fun getItemCount(): Int = inboxList.size
}