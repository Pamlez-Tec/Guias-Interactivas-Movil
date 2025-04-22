package com.example.guiaprueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            // Ir a la pantalla principal
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Para cerrar la pantalla de bienvenida
        }
    }
}
