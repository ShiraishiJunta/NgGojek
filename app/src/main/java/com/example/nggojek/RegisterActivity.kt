package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi Views
        val editUsername = findViewById<EditText>(R.id.editTextUsername)
        val editEmail = findViewById<EditText>(R.id.editTextEmail)
        val editPassword = findViewById<EditText>(R.id.editTextPassword)
        val editConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.buttonRegister)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        // Tombol Back
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Logika Register
        btnRegister.setOnClickListener {
            val username = editUsername.text.toString()
            val email = editEmail.text.toString()
            val passwd = editPassword.text.toString()
            val confirm = editConfirmPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || passwd.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwd != confirm) {
                Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_SHORT).show()

            // Mengarahkan ke Login
            val intent = Intent(this, LoginActivity::class.java)
            // Menghapus stack agar tidak masuk halaman register
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}