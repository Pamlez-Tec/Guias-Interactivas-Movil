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

class UberInfoTutorialActivity : AppCompatActivity() {
    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View
    private lateinit var progressBar: View

    private val stepLayouts = listOf(
        R.layout.tutorial_uber_step_bienvenida,
        R.layout.tutorial_uber_step_bienvenida,
        R.layout.tutorial_uber_step_registro,
        R.layout.tutorial_uber_step_registro,
        R.layout.tutorial_uber_step_activating_location
    )

    private val guideTexts = listOf(
        "Bienvenido al tutorial de cómo usar Uber.",
        "Iniciaremos la aplicación presionando el botón Get Started",
        "Acá entraremos en el área de registro. Podemos observar que hay distintas opciones a seleccionar",
        "En este caso, lo que haremos es asignar un número telefónico, digita un número telefónico en la parte de arriba y presiona continuar",
        "Y de esta forma, luego de una carga, finalmente habremos ingresado al sistema, presiona la X en caso de desear salir"
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
            1 -> { //Paso de Bienvenida
                val btn = view.findViewById<LinearLayout>(R.id.btnGetStartedUber)
                btn?.let {
                    highlightView(it)
                }
                btn?.setOnClickListener { btnNext.performClick() }
            }
            3 -> { //Paso de Registro
                val phoneInput = view.findViewById<EditText>(R.id.etPhoneNumber)
                val btn = view.findViewById<LinearLayout>(R.id.btnContinue)
                phoneInput?.setText("8888-8888")
                btn?.let {
                    highlightView(it)
                }
                btn?.setOnClickListener { btnNext.performClick() }
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