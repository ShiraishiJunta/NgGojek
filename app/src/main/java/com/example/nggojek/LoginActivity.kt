package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// PENTING: Import HomeActivity dari package 'home'
import com.example.nggojek.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi View
        val editTextEmailAddress: EditText = findViewById(R.id.editTextEmailAddress)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val goToRegister: TextView = findViewById(R.id.goToRegister)

        // Hardcode akun untuk testing
        val email = "a"
        val passwd = "a"

        // Pindah ke halaman Register
        goToRegister.setOnClickListener {
            val intentLoginToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentLoginToRegister)
        }

        // Logika Tombol Login
        buttonLogin.setOnClickListener {
            val username: String = editTextEmailAddress.text.toString().trim()
            val password: String = editTextPassword.text.toString().trim()

            // 1. Validasi Input Kosong
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email & Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Cek Kesesuaian Akun
            if (username == email && password == passwd) {
                val intentLoginToMain = Intent(this, HomeActivity::class.java)
                intentLoginToMain.putExtra(KEY_USERNAME, username)
                startActivity(intentLoginToMain)
                finish()

                Toast.makeText(this, "Selamat Datang, $username", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val KEY_USERNAME = "username"
    }
}