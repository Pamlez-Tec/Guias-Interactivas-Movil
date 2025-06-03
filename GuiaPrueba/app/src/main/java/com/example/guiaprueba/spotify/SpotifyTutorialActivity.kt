package com.example.guiaprueba.spotify

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.guiaprueba.R
import android.content.Intent

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
        "Presione el botón 'Registrarte gratis' para iniciar el proceso.",
        "Seleccione su método de registro, en este caso seleccione 'Correo electrónico'. La primera opción.",
        "Encima del botón 'Siguiente', escriba su correo electrónico. Revise bien el formato.",
        "Escriba una contraseña segura. Debe tener 10 letras o números.",
        "Seleccione su fecha de nacimiento. Debe tener cuidado con el formato, primero va el día con dos números, luego el mes con dos números y de último el año completo, con cuatro números.",
        "Seleccione su género, dándole click a un círculo hasta que cambie de color a morado.",
        "Escriba su nombre y acepte los términos y condiciones que aparecen debajo del nombre elegido.",
        "Para registrarse completamente debe presionar el botón 'Crear cuenta'. Fin del tutorial, para salir presione la X"
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
                val editEmail = view.findViewById<EditText>(R.id.editEmail)

                btn?.setOnClickListener {
                    val emailText = editEmail?.text.toString().trim()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

                    when {
                        emailText.isEmpty() -> {
                            Toast.makeText(this, "Por favor ingresa tu correo.", Toast.LENGTH_SHORT).show()
                        }
                        !emailText.matches(emailPattern) -> {
                            Toast.makeText(this, "Formato de correo inválido.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            btnNext.performClick()
                        }
                    }
                }
            }
            3 -> {
                val btn = view.findViewById<Button>(R.id.btnNextPass)
                val editPass = view.findViewById<EditText>(R.id.editPassword)

                btn?.setOnClickListener {
                    val passText = editPass?.text.toString()

                    if (passText.length < 10) {
                        Toast.makeText(this, "La contraseña debe tener al menos 10 caracteres.", Toast.LENGTH_SHORT).show()
                    } else {
                        btnNext.performClick()
                    }
                }
            }
            4 -> {
                val btn = view.findViewById<Button>(R.id.btnNextFecha)
                val editDia = view.findViewById<EditText>(R.id.editDia)
                val editMes = view.findViewById<EditText>(R.id.editMes)
                val editAnio = view.findViewById<EditText>(R.id.editAnio)

                btn?.setOnClickListener {
                    val dia = editDia?.text.toString()
                    val mes = editMes?.text.toString()
                    val anio = editAnio?.text.toString()

                    if (dia.length != 2 || mes.length != 2 || anio.length != 4) {
                        Toast.makeText(this, "Por favor ingrese una fecha válida (DD/MM/AAAA).", Toast.LENGTH_LONG).show()
                    } else {
                        btnNext.performClick()
                    }
                }
            }

            5 -> {
                val btn = view.findViewById<Button>(R.id.btnNextGenero)
                val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupGenero)

                btn?.setOnClickListener {
                    val selectedId = radioGroup?.checkedRadioButtonId

                    if (selectedId == -1) {
                        Toast.makeText(this, "Por favor selecciona una opción.", Toast.LENGTH_SHORT).show()
                    } else {
                        btnNext.performClick()
                    }
                }
            }

            6 -> {
                val btn = view.findViewById<Button>(R.id.btnNextTerminos)
                val editNombre = view.findViewById<EditText>(R.id.editNombre)
                val checkTerminos = view.findViewById<CheckBox>(R.id.checkTerminos)

                btn?.setOnClickListener {
                    val nombre = editNombre?.text.toString().trim()
                    val aceptaTerminos = checkTerminos?.isChecked == true

                    when {
                        nombre.isEmpty() -> {
                            Toast.makeText(this, "Por favor ingresa tu nombre.", Toast.LENGTH_SHORT).show()
                        }
                        !aceptaTerminos -> {
                            Toast.makeText(this, "Debes aceptar los Términos de Uso para continuar.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            btnNext.performClick()
                        }
                    }
                }
            }

            7 -> {
                val btn = view.findViewById<Button>(R.id.btnCrearCuenta)
                btn?.setOnClickListener {
                    Toast.makeText(this, "¡Cuenta creada!", Toast.LENGTH_SHORT).show()
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
