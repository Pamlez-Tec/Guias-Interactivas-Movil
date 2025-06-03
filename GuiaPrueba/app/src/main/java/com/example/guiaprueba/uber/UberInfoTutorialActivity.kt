package com.example.guiaprueba.uber

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.EditText
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
        R.layout.tutorial_uber_step_registro
    )

    private val guideTexts = listOf(
        "Bienvenido al tutorial de cómo usar Uber.",
        "Iniciaremos la aplicación presionando el botón Get Started",
        "Acá entraremos en el área de registro. Podemos observar que hay distintas opciones a seleccionar",
        "En este caso, lo que haremos es asignar un número telefónico, digita un número telefónico en la parte de arriba y presiona continuar"
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
            minimizeTutorial();
        }

        loadStep()
    }

    private fun enableStepSpecificActions(view: View) {
        when (currentStep) {
            1 -> { //Paso de Bienvenida
                val btn = view.findViewById<Button>(R.id.btnGetStartedUber)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            3 -> { //Paso de Registro
                // Continue with phone button
                view.findViewById<Button>(R.id.btnContinue)?.setOnClickListener {
                    // Simulate phone entry
                    val phoneInput = view.findViewById<EditText>(R.id.etPhoneNumber)
                    phoneInput.setText("8888-8888") // Auto-fill phone number
                    Toast.makeText(this, "Número ingresado automáticamente", Toast.LENGTH_SHORT).show()
                    btnNext.performClick()
                }

                // Continue with email button
                view.findViewById<Button>(R.id.btnContinueEmail)?.setOnClickListener {
                    // Auto-fill email in next steps
                    Toast.makeText(this, "Seleccionado: Continuar con Email", Toast.LENGTH_SHORT).show()
                    btnNext.performClick()
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