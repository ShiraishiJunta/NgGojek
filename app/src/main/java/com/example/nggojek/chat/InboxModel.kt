package com.example.nggojek.chat

// =====================================================
// InboxModel: Model data untuk inbox chat
// Menyimpan informasi percakapan dalam daftar inbox
// =====================================================

data class InboxModel(
    val name: String,    // Nama driver atau lawan bicara
    val message: String, // Pesan terakhir dalam percakapan
    val time: String     // Waktu pesan terakhir dikirim
)