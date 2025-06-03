package com.example.guiaprueba.spotify

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class SpotifyTutorialPlaylistActivity : AppCompatActivity() {

    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View
    private var playlistName: String = "Mi playlist #1"

    private val stepLayouts = listOf(
        R.layout.tutorial_playlist_step_1,
        R.layout.tutorial_playlist_step_2,
        R.layout.tutorial_playlist_step_3,
        R.layout.tutorial_playlist_step_4,
        R.layout.tutorial_playlist_step_5,
    )

    private val guideTexts = listOf(
        "Estando en su biblioteca. Presione el ícono + en la esquina superior, a la par de la lupa, para crear una lista de reproducción.",
        "Seleccione 'Playlist' para continuar.",
        "Escriba un nombre para su lista de reproducción y presione 'Crear'.",
        "Su nueva lista de reproducción se ha creado. Presione 'Tu biblioteca' en la parte de abajo.",
        "Aquí se guardará su nueva lista de reproducción. Fin del tutorial, para salir presione la X."
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
            if (currentStep == 3) {
                currentStep = 4
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
                val btnAdd = view.findViewById<ImageView>(R.id.iconAdd)
                btnAdd?.setOnClickListener {
                    btnNext.performClick()
                }
            }

            1 -> {
                val btnPlaylist = view.findViewById<LinearLayout>(R.id.btnCrearPlaylist)
                btnPlaylist?.setOnClickListener {
                    btnNext.performClick()
                }
            }

            2 -> {
                val btnCrear = view.findViewById<Button>(R.id.btnCrear)
                val input = view.findViewById<EditText>(R.id.editNombrePlaylist)

                btnCrear?.setOnClickListener {
                    val nombre = input.text.toString()
                    if (nombre.isNotBlank()) {
                        playlistName = nombre
                        btnNext.performClick()
                    } else {
                        Toast.makeText(this, "Escribe un nombre para la playlist", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            3 -> {
                val textNombre = view.findViewById<TextView>(R.id.textPlaylistName)
                textNombre.text = playlistName

            }

            4 -> {
                val textPlaylist = view.findViewById<TextView>(R.id.textPlaylistGuardada)
                textPlaylist.text = playlistName
            }

        }
    }
}
