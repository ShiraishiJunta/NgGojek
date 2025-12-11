package com.example.nggojek.chat

// =====================================================
// ChatMessage: Model data untuk pesan chat
// Menyimpan isi pesan dan informasi pengirim
// =====================================================

data class ChatMessage(
    val message: String,    // Isi pesan yang dikirim
    val isSender: Boolean   // true = pesan dari pengguna (kanan), false = pesan dari driver (kiri)
)