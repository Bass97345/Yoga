package com.example.yoga.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.yoga.R


class Listener : Fragment(R.layout.fragment_listener) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val backBtn = view.findViewById<TextView>(R.id.pl_back)
        val source = arguments?.getString("source")

        backBtn.setOnClickListener {
            when (source) {
                "main" -> findNavController().popBackStack(R.id.main2, false)
                "profile" -> findNavController().popBackStack(R.id.profile, false)

            }
        }

    }
}