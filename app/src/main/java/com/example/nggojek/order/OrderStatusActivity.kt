package com.example.nggojek.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.nggojek.R

class OrderStatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val txtDriverName = findViewById<TextView>(R.id.txtDriverName)
        val txtPickup = findViewById<TextView>(R.id.txtPickup)
        val txtDestination = findViewById<TextView>(R.id.txtDestination)
        val txtPayment = findViewById<TextView>(R.id.txtPayment)

        // Receive intent data
        val driverName = intent.getStringExtra("driver_name")
        val pickup = intent.getStringExtra("pickup")
        val destination = intent.getStringExtra("destination")
        val payment = intent.getStringExtra("payment")

        txtDriverName.text = driverName
        txtPickup.text = pickup
        txtDestination.text = destination
        txtPayment.text = payment

    }
}
