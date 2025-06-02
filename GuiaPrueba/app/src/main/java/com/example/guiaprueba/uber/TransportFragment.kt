package com.example.guiaprueba.uber

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.guiaprueba.R

class TransportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transport, container, false)

        val btnUber = view.findViewById<Button>(R.id.btnUberTutorial)
        btnUber.setOnClickListener {
            val intent = Intent(requireContext(), UberTutorialMenuActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
