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
import com.example.nggojek.home.HomeActivity

// =====================================================
// LoginActivity: Halaman untuk login masuk aplikasi
// Pengguna memasukkan email dan password
// =====================================================

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Mengatur padding agar tidak tertutup status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Menghubungkan komponen dengan ID dari layout
        val editTextEmailAddress: EditText = findViewById(R.id.editTextEmailAddress)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val goToRegister: TextView = findViewById(R.id.goToRegister)

        // Akun dummy untuk testing (email: a, password: a)
        val email = "a"
        val passwd = "a"

        // Tombol untuk pindah ke halaman Register
        goToRegister.setOnClickListener {
            val intentLoginToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentLoginToRegister)
        }

        // Tombol untuk login
        buttonLogin.setOnClickListener {
            val username: String = editTextEmailAddress.text.toString().trim()
            val password: String = editTextPassword.text.toString().trim()

            // Validasi: Email dan password tidak boleh kosong
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email & Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mengecek kesesuaian email dan password
            if (username == email && password == passwd) {
                // Login berhasil - pindah ke halaman Home
                val intentLoginToMain = Intent(this, HomeActivity::class.java)
                intentLoginToMain.putExtra(KEY_USERNAME, username)
                startActivity(intentLoginToMain)
                finish()

                Toast.makeText(this, "Selamat Datang, $username", Toast.LENGTH_SHORT).show()
            } else {
                // Login gagal - email atau password salah
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Konstanta untuk key username
    companion object {
        const val KEY_USERNAME = "username"
    }
}