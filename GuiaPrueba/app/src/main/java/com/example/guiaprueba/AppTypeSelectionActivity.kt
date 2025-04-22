package com.example.guiaprueba

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class AppTypeSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_type_selection)

        val btnAceptar = findViewById<TextView>(R.id.btnAceptar)
        val btnRegresar = findViewById<TextView>(R.id.btnRegresar)

        btnAceptar.setTextColor(ContextCompat.getColor(this, android.R.color.holo_purple))
        btnRegresar.setTextColor(ContextCompat.getColor(this, android.R.color.holo_purple))

        btnAceptar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}



