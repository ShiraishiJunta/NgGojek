package com.example.nggojek

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class OrderStatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val txtDriverName = findViewById<TextView>(R.id.txtDriverName)
        val txtPickup = findViewById<TextView>(R.id.txtPickup)
        val txtDestination = findViewById<TextView>(R.id.txtDestination)
        val txtPayment = findViewById<TextView>(R.id.txtPayment)
        val btnCall = findViewById<ImageView>(R.id.btnCall)
        val btnChat = findViewById<ImageView>(R.id.btnChat)

        // Receive intent data
        val driverName = intent.getStringExtra("driver_name")
        val pickup = intent.getStringExtra("pickup")
        val destination = intent.getStringExtra("destination")
        val payment = intent.getStringExtra("payment")
        val driverPhone = intent.getStringExtra("driver_phone")

        txtDriverName.text = driverName
        txtPickup.text = pickup
        txtDestination.text = destination
        txtPayment.text = payment

        // ACTION: CALL DRIVER
        btnCall.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:$driverPhone")
            startActivity(phoneIntent)
        }

        // ACTION: CHAT DRIVER
        btnChat.setOnClickListener {
            val chatIntent = Intent(this, ChatActivity::class.java)
            chatIntent.putExtra("driver_name", driverName)
            startActivity(chatIntent)
        }
    }
}
