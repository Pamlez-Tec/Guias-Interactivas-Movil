package com.example.guiaprueba.uber

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class UberTutorialMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uber_tutorial_menu)

        findViewById<Button>(R.id.btnTutorialRegistro).setOnClickListener {
            startActivity(Intent(this, UberInfoTutorialActivity::class.java))
        }

        findViewById<Button>(R.id.btnTutorialAsignarUbicacion).setOnClickListener {
            // más adelante
        }

        findViewById<Button>(R.id.btnTutorialPedirUber).setOnClickListener {
            // más adelante
        }

        findViewById<Button>(R.id.btnTutorialPagoUber).setOnClickListener {
            // más adelante
        }

        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }
}
