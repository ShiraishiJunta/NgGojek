package com.example.nggojek.riwayat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nggojek.R

class RiwayatAdapter(
    private val items: List<Riwayat>,
    private val onItemClick: (Riwayat) -> Unit // 1. Tambahkan parameter fungsi klik
) : RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    class RiwayatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNamaDriver: TextView = itemView.findViewById(R.id.txtNamaDriver)
        val txtTotal: TextView = itemView.findViewById(R.id.txtTotal)
        val txtHarga: TextView = itemView.findViewById(R.id.txtHarga)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val imgDriver: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat, parent, false)
        return RiwayatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val item = items[position]

        holder.imgDriver.setImageResource(R.drawable.profile)
        holder.txtNamaDriver.text = item.namaDriver
        holder.txtTotal.text = "Total"
        holder.txtHarga.text = item.totalHarga
        holder.ratingBar.rating = item.rating

        // 2. Pasang Listener Klik pada Item
        holder.itemView.setOnClickListener {
            onItemClick(item) // Panggil fungsi saat diklik
        }
    }

    override fun getItemCount(): Int = items.size
}