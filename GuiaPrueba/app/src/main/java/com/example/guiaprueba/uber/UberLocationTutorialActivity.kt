package com.example.guiaprueba.uber

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class UberLocationTutorialActivity : AppCompatActivity() {
    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View
    private lateinit var progressBar: View

    private val stepLayouts = listOf(
        R.layout.tutorial_uber_step_activating_location,
        R.layout.tutorial_uber_step_activating_location,
        R.layout.tutorial_uber_step_activating_location,
        R.layout.tutorial_uber_step_activating_location,
        R.layout.tutorial_uber_step1_pedir_viaje
    )

    private val guideTexts = listOf(
        "Ahora, estando acá habrás notado que la aplicación te pide la ubicación",
        "Para activarlo desde la aplicación, lo que haremos será dar click a esa parte que nos pide que la habilitemos",
        "Al hacerlo nos saldrá un popup, podemos leerlo, pero basicamente nos está pidiendo permiso de activar la ubicacion",
        "Presionas el botón de encender en este caso",
        "Y ahora, nuestra aplicacion tiene la ubicacion activada, un paso menos para poder pedir un Uber"
    )

    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uber_tutorial)

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
            if (currentStep == stepLayouts.lastIndex) {
                finish()
            } else {
                minimizeTutorial()
            }
        }

        loadStep()
    }

    private fun highlightView(view: View){
        val highlightColor = resources.getColor(android.R.color.holo_green_light)
        val originalColor = view.background

        view.setBackgroundColor(highlightColor)
        view.postDelayed({
            view.background = originalColor
        }, 1500)
    }

    private fun enableStepSpecificActions(view: View) {
        when (currentStep) {
            1 -> { //Activamos el boton
                val location = view.findViewById<LinearLayout>(R.id.locationAdvice)
                location?.setOnClickListener{ btnNext.performClick() }
            }
            2 -> { //Paso de activar el popup o modal
                val modal = view.findViewById<FrameLayout>(R.id.locationPopup)
                modal?.visibility = View.VISIBLE;
            }
            3 -> { //Paso de Activar el boton de
                val modal = view.findViewById<FrameLayout>(R.id.locationPopup)
                modal?.visibility = View.VISIBLE;
                val btn = view.findViewById<LinearLayout>(R.id.turnOnLocation)
                btn?.let {
                    highlightView(it)
                    it.setOnClickListener { btnNext.performClick() }
                }
            }
        }
    }

    private fun minimizeTutorial(){
        overlay.visibility = View.GONE
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
}