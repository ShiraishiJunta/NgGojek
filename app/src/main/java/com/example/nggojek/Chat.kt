package com.example.nggojek

data class ChatMessage(
    val message: String = "",
    val isSender: Boolean = false,   // true = bubble kanan
    val timestamp: Long = System.currentTimeMillis()
)
