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
        }
    }

    class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BlankFragment() // Puedes renombrar y personalizar luego
                1 -> BlankFragment()
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