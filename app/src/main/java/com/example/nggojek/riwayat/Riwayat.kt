package com.example.nggojek.riwayat

data class Riwayat(
    val namaDriver: String,
    val totalHarga: String,
    val rating: Float,
    val alamatJemput: String,
    val alamatTujuan: String,
    val jenisLayanan: String,
    val ulasan: String
)