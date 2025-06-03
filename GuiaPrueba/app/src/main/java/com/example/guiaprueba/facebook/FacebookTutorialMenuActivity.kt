package com.example.guiaprueba.facebook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class FacebookTutorialMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_tutorial_menu)

        findViewById<Button>(R.id.btnAgregarAmigo).setOnClickListener {
            startActivity(Intent(this, FacebookTutorialAddFriendActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerNotificaciones).setOnClickListener {
        }

        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }
}
