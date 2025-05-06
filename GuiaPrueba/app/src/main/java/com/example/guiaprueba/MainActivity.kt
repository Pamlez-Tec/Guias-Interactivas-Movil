    package com.example.guiaprueba

    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import androidx.viewpager2.widget.ViewPager2
    import com.google.android.material.tabs.TabLayout
    import com.google.android.material.tabs.TabLayoutMediator
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.FragmentActivity
    import androidx.viewpager2.adapter.FragmentStateAdapter
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageButton
    import android.widget.Toast
    import android.widget.TextView


    class MainActivity : AppCompatActivity() {

        private lateinit var tabLayout: TabLayout
        private lateinit var viewPager: ViewPager2

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val btnHelp = findViewById<ImageButton>(R.id.btnHelp)
            val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

            btnHelp.setOnClickListener {
                Toast.makeText(this, "adios", Toast.LENGTH_SHORT).show()
            }

            btnProfile.setOnClickListener {
                Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show()
            }

            tabLayout = findViewById(R.id.tabLayout)
            viewPager = findViewById(R.id.viewPager)

            val adapter = ViewPagerAdapter(this)
            viewPager.adapter = adapter

            val tabTitles = arrayOf("Música", "Redes Sociales", "Transporte")

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()


            val guideOverlay = findViewById<View>(R.id.guideOverlay)
            val guideText = findViewById<TextView>(R.id.guideText)
            val btnNext = findViewById<ImageButton>(R.id.btnNext)
            val btnBack = findViewById<ImageButton>(R.id.btnBack)
            val btnClose = findViewById<ImageButton>(R.id.btnClose)
            btnClose.setOnClickListener {
                guideOverlay.visibility = View.GONE
            }


            val guideSteps = listOf(
                "Bienvenido a la Guía de aplicaciones. Esperamos que su aprendizaje resulte agradable.\n\n" +
                        "Estos mensajes le guiarán paso a paso, en este caso le enseñará a utilizar esta aplicación. " +
                        "Para continuar con el tutorial presione la flecha que se encuentra en la parte inferior derecha.",

                "En caso de que haya presionado sin querer el botón para avanzar el tutorial, puede volver al mensaje anterior " +
                        "al presionar la flecha que está en la parte inferior izquierda.",

                "Los tutoriales solamente aparecerán una vez, esto le permetirá probar libremente las funciones que simula la app, " +
                        "si desea volver a ver el tutorial de la sección en la que se encuentra, presione el botón de signo de pregunta.",

                "Organizamos los tutoriales en categorías que le ayudarán a encontrar más fácilmente el que desea realizar, para " +
                        "elegir una categoría debe presionar el nombre que se encuentra en la barra de categorías. \n",

                "¡Felicidades!\n" +
                        "Ya sabes cómo utilizar la applicación. Ahora puedes elegir cualquier tutorial de las aplicaciones que desee."
            )
            var currentStep = 0

            fun updateGuideStep() {
                guideText.text = guideSteps[currentStep]
                btnBack.visibility = if (currentStep == 0) View.GONE else View.VISIBLE
                btnNext.visibility = if (currentStep == guideSteps.size - 1) View.GONE else View.VISIBLE
            }

            btnHelp.setOnClickListener {
                guideOverlay.visibility = View.VISIBLE
                currentStep = 0
                updateGuideStep()
            }

            btnNext.setOnClickListener {
                if (currentStep < guideSteps.size - 1) {
                    currentStep++
                    updateGuideStep()
                }
            }

            btnBack.setOnClickListener {
                if (currentStep > 0) {
                    currentStep--
                    updateGuideStep()
                }
            }

            guideOverlay.visibility = View.VISIBLE
            currentStep = 0
            updateGuideStep()
            //guideOverlay.setOnClickListener {
            //    guideOverlay.visibility = View.GONE
            //}

        }
    }

    class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BlankFragment()
                1 -> SocialFragment()
                2 -> BlankFragment()
                else -> BlankFragment()
            }
        }
    }

    class BlankFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return View(context).apply { setBackgroundColor(0xFFE0E0E0.toInt()) } // gris claro como en la imagen
        }
    }