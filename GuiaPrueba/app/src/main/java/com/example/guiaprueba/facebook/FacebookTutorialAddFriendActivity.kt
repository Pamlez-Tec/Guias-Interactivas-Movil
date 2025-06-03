package com.example.guiaprueba.facebook

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R

class FacebookTutorialAddFriendActivity  : AppCompatActivity() {

    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View

    private val stepLayouts = listOf(
        R.layout.tutorial_facebook_step_1,
        R.layout.tutorial_facebook_addfriend_step_2,
        R.layout.tutorial_facebook_addfriend_step_3,
        R.layout.tutorial_facebook_addfriend_step_4,
    )

    private val guideTexts = listOf(
        "En la página principal. Presione el botón en forma de lupa, en la parte de arriba, para buscar a una persona.",
        "A la par de la lupa, escriba el nombre del amigo que desea agregar.",
        "Se presentarán varios perfiles. Seleccione a la persona que busca, dando un click en el nombre.",
        "Presione el botón 'Agregar a amigos' para enviar la solicitud de amistad. Si desea salir del tutorial, presione la X."
    )

    private var currentStep = 0
    private var nombreBuscado: String = ""

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
                val lupa = view.findViewById<ImageView>(R.id.iconSearch)
                lupa?.setOnClickListener { btnNext.performClick() }
            }
            1 -> {
                val btnBuscar = view.findViewById<Button>(R.id.btnBuscarAmigo)
                val campoTexto = view.findViewById<EditText>(R.id.editBuscarNombre)

                btnBuscar?.setOnClickListener {
                    val nombreIngresado = campoTexto.text.toString().trim()
                    if (nombreIngresado.isNotBlank()) {
                        nombreBuscado = nombreIngresado
                        currentStep++
                        loadStep()
                    } else {
                        Toast.makeText(this, "Escriba un nombre para buscar", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            2 -> {
                val resultadosContainer = view.findViewById<LinearLayout>(R.id.resultadosContainer)
                val apellidos = listOf("Ramírez", "Gómez", "Hernández")

                apellidos.forEach { apellido ->
                    val resultadoItem = layoutInflater.inflate(R.layout.item_resultado_persona, resultadosContainer, false)

                    val imagen = resultadoItem.findViewById<ImageView>(R.id.fotoPerfilResultado)
                    val texto = resultadoItem.findViewById<TextView>(R.id.nombreResultado)

                    val nombreCompleto = "$nombreBuscado $apellido"
                    texto.text = nombreCompleto

                    resultadoItem.setOnClickListener {
                        nombreBuscado = nombreCompleto
                        currentStep++
                        loadStep()
                    }

                    resultadosContainer.addView(resultadoItem)
                }
            }

            3 -> {
                val fotoPerfil = view.findViewById<ImageView>(R.id.fotoPerfilAmigo)
                val nombreTexto = view.findViewById<TextView>(R.id.nombrePerfilAmigo)
                val btnAgregar = view.findViewById<Button>(R.id.btnAgregarAmigoFinal)

                nombreTexto.text = nombreBuscado

                btnAgregar.setOnClickListener {
                    Toast.makeText(this, "Solicitud enviada a $nombreBuscado", Toast.LENGTH_LONG).show()

                    btnAgregar.text = "Solicitud enviada"
                    btnAgregar.isEnabled = false
                    btnAgregar.setBackgroundColor(android.graphics.Color.GRAY)

                }
            }

        }

    }
}