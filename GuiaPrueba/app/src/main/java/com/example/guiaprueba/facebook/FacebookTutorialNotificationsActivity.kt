package com.example.guiaprueba.facebook

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R
import android.widget.ImageView

class FacebookTutorialNotificationsActivity : AppCompatActivity() {

    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View

    private val stepLayouts = listOf(
        R.layout.tutorial_facebook_notification_step_1,
        R.layout.tutorial_facebook_notifications_step_2 ,
        R.layout.tutorial_facebook_notifications_step_3
    )

    private val guideTexts = listOf(
        "En la página principal. Presione el botón en forma de campana, en la parte superior, para ver las notificaciones.",
        "Aquí puede ver sus notificaciones. Presione la primera notificación, la de Ana, para ver su contenido.",
        "Aquí puede ver tanto su publicación como el comentario de Ana. Fin del tutorial, para salir presione la X."
    )

    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_tutorial)

        tutorialContainer = findViewById(R.id.tutorialContainer)
        guideText = findViewById(R.id.guideText)
        btnNext = findViewById(R.id.btnNext)
        btnBack = findViewById(R.id.btnBack)
        btnClose = findViewById(R.id.btnClose)
        overlay = findViewById(R.id.guideContainer)

        btnNext.setOnClickListener {
            if (currentStep < stepLayouts.lastIndex) {
                currentStep++
                loadStep()
            }
        }

        btnBack.setOnClickListener {
            if (currentStep > 0) {
                currentStep--
                loadStep()
            }
        }

        btnClose.setOnClickListener {
            finish()
        }

        loadStep()
    }

    private fun loadStep() {
        val view = layoutInflater.inflate(stepLayouts[currentStep], null)
        tutorialContainer.removeAllViews()
        tutorialContainer.addView(view)

        enableStepSpecificActions(view)

        guideText.text = guideTexts[currentStep]
        btnBack.visibility = if (currentStep == 0) View.GONE else View.VISIBLE
        btnNext.visibility = if (currentStep == stepLayouts.lastIndex) View.GONE else View.VISIBLE
        overlay.visibility = View.VISIBLE
    }

    private fun enableStepSpecificActions(view: View) {
        when (currentStep) {
            0 -> {
                try {
                    val bell = view.findViewById<ImageView>(R.id.iconNotification)
                    bell?.setOnClickListener { btnNext.performClick() }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            1 -> {
                val item = view.findViewById<View>(R.id.notificationItem)
                item?.setOnClickListener {
                    currentStep++
                    loadStep()
                }
            }
        }
    }
}
