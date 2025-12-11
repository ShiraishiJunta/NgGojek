package com.example.nggojek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatInboxAdapter(
    private val inboxList: List<InboxModel>,
    private val onItemClick: (InboxModel) -> Unit
) : RecyclerView.Adapter<ChatInboxAdapter.InboxViewHolder>() {

    class InboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPreview: TextView = itemView.findViewById(R.id.tvPreview)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_box, parent, false)
        return InboxViewHolder(view)
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
        val item = inboxList[position]

        holder.tvName.text = item.name
        holder.tvPreview.text = item.message
        holder.tvTime.text = item.time

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = inboxList.size
}