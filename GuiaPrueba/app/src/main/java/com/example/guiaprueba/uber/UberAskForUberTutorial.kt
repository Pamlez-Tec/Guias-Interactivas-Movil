package com.example.guiaprueba.uber

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.EditText
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class UberAskForUberTutorial : AppCompatActivity() {
    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View
    private lateinit var progressBar: View

    private val stepLayouts = listOf(
        R.layout.tutorial_uber_step1_pedir_viaje,
        R.layout.tutorial_uber_step1_pedir_viaje,
        R.layout.tutorial_uber_step2_pedir_viaje,
        R.layout.tutorial_uber_step2_pedir_viaje,
        R.layout.tutorial_uber_step3_pedir_viaje,
        R.layout.tutorial_uber_step3_pedir_viaje,
        R.layout.tutorial_uber_step4_pedir_viaje
        )

    private val guideTexts = listOf(
        "Ahora si, vamos a pedir un Uber por primera vez",
        "Para esto, debemos de seleccionar la parte del punto de recogida",
        "Una vez acá, estamos en la sección para seleccionar la ubicación, la primera es el punto en el que estás actualmente (o el punto más cercano)",
        "Acá por ejemplo, estamos en Maderas AD, el punto a seleccionar es el segundo",
        "Una vez este punto sea completado, somos enviados a la pagina para seleccionar el Uber que deseemos usar",
        "Podrás notar que en este caso falta el método de pago, eso es parte del otro tutorial, así que saltemos al siguiente paso",
        "Ahora, solo debes de seleccionar el Uber X, y ya debería de funcionar (presiona X para salir del tutorial)"
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
            1 -> { //Paso de Accediendo a metodo de pago
                val recogidaLayout = view.findViewById<LinearLayout>(R.id.searchLocation)
                recogidaLayout?.let {
                    highlightView(it)
                }
                recogidaLayout?.setOnClickListener{ btnNext.performClick() }
            }
            3 -> { //Paso de asignar valor de forma automática
                val uberCompletion = view.findViewById<EditText>(R.id.etBeginLocation)
                uberCompletion?.setText("Maderas AD")
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