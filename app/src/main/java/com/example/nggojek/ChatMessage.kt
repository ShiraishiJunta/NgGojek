package com.example.nggojek

data class ChatMessage(
    val message: String,
    val isSender: Boolean // true = Kita, false = Driver
)