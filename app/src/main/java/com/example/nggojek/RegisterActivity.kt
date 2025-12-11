package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// =====================================================
// RegisterActivity: Halaman untuk registrasi akun baru
// Pengguna mengisi username, email, password, dan konfirmasi password
// =====================================================

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Menghubungkan komponen dengan ID dari layout
        val editUsername = findViewById<EditText>(R.id.editTextUsername)
        val editEmail = findViewById<EditText>(R.id.editTextEmail)
        val editPassword = findViewById<EditText>(R.id.editTextPassword)
        val editConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.buttonRegister)

        // Tombol untuk melakukan registrasi
        btnRegister.setOnClickListener {
            // Mengambil input dari pengguna
            val username = editUsername.text.toString()
            val email = editEmail.text.toString()
            val passwd = editPassword.text.toString()
            val confirm = editConfirmPassword.text.toString()

            // Validasi: Semua field harus diisi
            if (username.isEmpty() || email.isEmpty() || passwd.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi: Password harus sama dengan konfirmasi password
            if (passwd != confirm) {
                Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registrasi berhasil - kembali ke halaman login
            Toast.makeText(this, "Registrasi Berhasil...", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
