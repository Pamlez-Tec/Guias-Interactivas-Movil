package com.example.guiaprueba.spotify

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.guiaprueba.R

class TransportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_music, container, false)

        val btnUberEats = view.findViewById<Button>(R.id.btnSpotifyTutorial)
        btnUberEats.setOnClickListener {
            val intent = Intent(requireContext(), UberTutorialMenuActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
