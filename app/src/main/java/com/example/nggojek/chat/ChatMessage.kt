package com.example.nggojek.chat

data class ChatMessage(
    val message: String,
    val isSender: Boolean // true = Kita, false = Driver
)