package com.example.guiaprueba

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.guiaprueba.instagram.InstagramPrincipal
import com.example.guiaprueba.facebook.FacebookTutorialMenuActivity

class SocialFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_social, container, false)

        val btnTutorial = view.findViewById<Button>(R.id.btnTutorial)
        btnTutorial.setOnClickListener {
            Toast.makeText(context, "Iniciando tutorial de Instagram...", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(), InstagramPrincipal::class.java)
            intent.putExtra("userId", 1)
            startActivity(intent)
        }

            val btnFacebookTutorial = view.findViewById<Button>(R.id.btnFacebookTutorial)
            btnFacebookTutorial.setOnClickListener {
            val intent = Intent(requireContext(), FacebookTutorialMenuActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
