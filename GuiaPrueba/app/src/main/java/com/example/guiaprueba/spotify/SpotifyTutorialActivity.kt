package com.example.guiaprueba.spotify

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class SpotifyTutorialActivity : AppCompatActivity() {

    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View

    private val stepLayouts = listOf(
        R.layout.tutorial_spotify_step_bienvenida,
        R.layout.tutorial_spotify_step_metodo_registro,
        R.layout.tutorial_spotify_step_ingresar_email,
        R.layout.tutorial_spotify_step_crear_contrasena,
        R.layout.tutorial_spotify_step_fecha_nacimiento,
        R.layout.tutorial_spotify_step_genero,
        R.layout.tutorial_spotify_step_nombre_y_terminos,
        R.layout.tutorial_spotify_step_confirmar_cuenta
    )

    private val guideTexts = listOf(
        "Presione 'Registrarte gratis' para iniciar el proceso.",
        "Seleccione su método de registro (email o Google).",
        "Ingrese su correo electrónico.",
        "Cree una contraseña segura.",
        "Seleccione su fecha de nacimiento.",
        "Seleccione su género.",
        "Escriba su nombre y acepte los términos y condiciones.",
        "Presione 'Crear cuenta' para finalizar el registro."
    )

    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_tutorial)

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
            // Ir al menú principal de Spotify
            val intent = Intent(this, SpotifyTutorialMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        loadStep()
    }

    private fun enableStepSpecificActions(view: View) {
        when (currentStep) {
            0 -> {
                val btn = view.findViewById<Button>(R.id.btnRegister)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            1 -> {
                val btn = view.findViewById<Button>(R.id.btnContinuarEmail)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            2 -> {
                val btn = view.findViewById<Button>(R.id.btnNextEmail)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            3 -> {
                val btn = view.findViewById<Button>(R.id.btnNextPass)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            4 -> {
                val btn = view.findViewById<Button>(R.id.btnNextFecha)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            5 -> {
                val btn = view.findViewById<Button>(R.id.btnNextGenero)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            6 -> {
                val btn = view.findViewById<Button>(R.id.btnNextTerminos)
                btn?.setOnClickListener { btnNext.performClick() }
            }
            7 -> {
                val btn = view.findViewById<Button>(R.id.btnCrearCuenta)
                btn?.setOnClickListener {
                    Toast.makeText(this, "¡Cuenta creada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
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
