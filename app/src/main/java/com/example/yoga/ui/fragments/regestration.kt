package com.example.yoga.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.yoga.R


class regestration : Fragment(R.layout.fragment_regestration) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backRegBtn = view.findViewById<TextView>(R.id.reg_back)

        backRegBtn.setOnClickListener{

            findNavController().navigate(R.id.action_regestration_to_onboarding)
        }
    }
}