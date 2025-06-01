package com.example.guiaprueba.spotify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class SpotifyTutorialMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_tutorial_menu)

        findViewById<Button>(R.id.btnTutorialRegistro).setOnClickListener {
            startActivity(Intent(this, SpotifyTutorialActivity::class.java))
        }

        findViewById<Button>(R.id.btnTutorialBiblioteca).setOnClickListener {
            // más adelante
        }

        findViewById<Button>(R.id.btnTutorialPlaylist).setOnClickListener {
            // más adelante
        }

        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }
}
