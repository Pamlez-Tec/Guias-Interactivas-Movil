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

class UberSetPaymentTutorial : AppCompatActivity() {
    private lateinit var tutorialContainer: FrameLayout
    private lateinit var guideText: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnClose: ImageButton
    private lateinit var overlay: View
    private lateinit var progressBar: View

    private val stepLayouts = listOf(
        R.layout.tutorial_uber_step3_pedir_viaje,
        R.layout.tutorial_uber_step3_pedir_viaje,
        R.layout.tutorial_uber_step1_elegir_metodo_pago,
        R.layout.tutorial_uber_step1_elegir_metodo_pago,
        R.layout.tutorial_uber_step2_elegir_metodo_pago,
        R.layout.tutorial_uber_step2_elegir_metodo_pago,
        R.layout.tutorial_uber_step4_pedir_viaje,
        )

    private val guideTexts = listOf(
        "Estabas listo para pedir tu primer uber pero... se te olvidó asignar el pago. Tranquilo, te enseñaremos como hacerlo",
        "Primero, daremos click a esa opción de Agregar Método de Pago",
        "Acá, se nos muestra una serie de opciones a escoger como método de pago",
        "En nuestro caso, escogeremos la de la tarjeta de crédito, tocamos la opcion de Tarjeta de crédito o débito",
        "Acá observaremos que tenemos para introducir la información de la tarjeta de crédito",
        "Por ahora, introduciremos un poco de datos ficticios, pero piensa que estás introduciendo tus datos, acá, lo que harás es darle al botón Siguiente",
        "Y de esta manera es como puedes encargarte de asignar el metodo de pago de forma permanente, para salir presiona la X"
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
                val creditCardOption = view.findViewById<LinearLayout>(R.id.creditCardOption)
                creditCardOption?.let {
                    highlightView(it)
                }
                creditCardOption?.setOnClickListener{ btnNext.performClick() }
            }
            3 -> { //Paso de Seleccionar metodo de pago
                val creditCardOption = view.findViewById<LinearLayout>(R.id.creditCardOption)
                creditCardOption?.let {
                    highlightView(it)
                }
                creditCardOption?.setOnClickListener{ btnNext.performClick() }
            }
            5 -> { //Paso de rellenar con información los datos y highlight el boton
                val cardInput = view.findViewById<EditText>(R.id.etCreditCardNumber)
                var expDate = view.findViewById<EditText>(R.id.etExpirationDate);
                var cvv = view.findViewById<EditText>(R.id.etCVV);
                var surname = view.findViewById<EditText>(R.id.surname);

                cardInput?.setText("4111 1111 1111 1111")
                expDate?.setText("12/28")
                cvv?.setText("***")
                surname?.setText("JONATHAN SOFEIFA D")

                val creditCardOption = view.findViewById<LinearLayout>(R.id.buttonContinue)
                creditCardOption?.let {
                    highlightView(it)
                }

                creditCardOption?.setOnClickListener{ btnNext.performClick() }
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