package com.example.guiaprueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.instagram.InstagramPrincipal

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            //val intent = Intent(this, InstagramPrincipal::class.java)
            //startActivity(intent)
            startActivity(Intent(this, AppTypeSelectionActivity::class.java))
            finish()
        }
    }
}
