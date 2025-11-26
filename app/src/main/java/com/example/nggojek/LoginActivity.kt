package com.example.nggojek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import androidx.core.view.WindowCompat

class LoginActivity : AppCompatActivity() , View.OnClickListener {

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonLogin->{
                Toast.makeText(this,"Button click via interface", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val editTextEmailAddress: EditText = findViewById(R.id.editTextEmailAddress)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val username: String = editTextEmailAddress.text.toString()

            val intentLoginToMain = Intent(this, HomepageActivity::class.java)
            intentLoginToMain.putExtra(KEY_USERNAME,username)
            startActivity(intentLoginToMain)

            Toast.makeText(this,"Selamat Datang, $username", Toast.LENGTH_SHORT).show()
        }
    }
    companion object{
        const val KEY_USERNAME = "username"
    }

}
