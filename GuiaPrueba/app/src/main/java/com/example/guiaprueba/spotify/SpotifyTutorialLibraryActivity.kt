package com.example.guiaprueba.spotify

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class SpotifyTutorialLibraryActivity : AppCompatActivity() {

    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View

    private val stepLayouts = listOf(
        R.layout.tutorial_spotify_lib_step_1,
        R.layout.tutorial_spotify_lib_step_2,
        R.layout.tutorial_spotify_lib_step_3,
    )

    private val guideTexts = listOf(
        "Seleccione el artista que más le guste, presionando la foto.",
        "Presione el botón +, debajo de la foto del artista, para añadirlo a tu biblioteca. El botón + " +
                "cambia a verde cuando se hizo correctamente.",
        "Aquí puedes ver los artistas que añadiste. Y reproducirlos cuando quieras. Para finalizar presiona la " +
                "X de arriba de este diálogo"
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
            finish()
        }

        val btnTuBiblioteca = findViewById<TextView>(R.id.btnTuBiblioteca)
        btnTuBiblioteca.setOnClickListener {
            if (currentStep == 1) {
                currentStep = 2
                loadStep()
            }
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
                val card = view.findViewById<LinearLayout>(R.id.cardLedZeppelin)
                card?.setOnClickListener {
                    btnNext.performClick()
                }
            }

            1 -> {
                val btn = view.findViewById<ImageView>(R.id.iconoAgregar)
                val guideText = findViewById<TextView>(R.id.guideText)

                btn?.setOnClickListener {
                    btn.setImageResource(R.drawable.ic_check)
                    guideText?.text = "El artista ha sido añadido. Presiona el botón 'Tu biblioteca', en la parte inferior."
                    Toast.makeText(this, "Se agregó a Tu biblioteca", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}
